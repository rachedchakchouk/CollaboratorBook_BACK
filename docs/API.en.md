# CollaboratorBook back-end REST API

[Français](API.md) · **English**

Back to the [README](../README.en.md) · See also: [architecture](ARCHITECTURE.en.md) · [data model](DATA-MODEL.en.md)

## General notes

- **Addresses**: the front end calls each service directly: securityMs on `http://localhost:8087`, botRH on
  `http://localhost:8081`, businessMS on `http://localhost:8082`. All paths below are relative to these addresses.
- **Swagger UI**: `/swagger-ui.html` on each of these three services (OpenAPI description on `/v3/api-docs`).
- **Authentication**: no route requires a token or a role (see the Security section of the README). The front end
  still sends `Authorization: Bearer <access token>`; securityMs rejects an invalid token.
- **Format**: JSON. Request bodies are `XxxRequestDto` objects (see the [data model](DATA-MODEL.en.md)); responses are
  `XxxResponseDto` objects. Linking routes (`addTo...`, `...To...`) have no body.
- **Archiving**: `archive` sets `archived` to `true`, `noarchive` sets it back to `false`. The `active/...` and
  `archived/...` lists filter on this field.
- **Errors**: no common error format; an unknown ID results in a 500 response.
- Paths are reproduced as they are, spelling mistakes included (`comapny`, `upadte`, `byEmplyee`,
  `getemloyeebycompany`, `prouve`…).

## securityMs — `/api`

| Method | Path | Purpose |
|---|---|---|
| POST | `/api/login` | Sign-in. Form (or query) parameters `username` and `password`. Response: `access_Token`, `refresh_token`, `username`, `roles`. Handled by `CustomAuthenticationFilter`. |
| GET | `/api/token/refresh` | With `Authorization: Bearer <refresh_token>`: returns `Access_token` (new, 10 min) and `refresh_token`. 403 error with a JSON message if the token is invalid. |
| GET | `/api/users` | List of users (`User` entities, hashed password included). |
| GET | `/api/user/{username}` | User by username; empty body if it does not exist. |
| GET | `/api/employee/{username}` | Employee (botRH) linked to the user, fetched through OpenFeign. |
| POST | `/api/user/save` | Creates a user. `User` body: `username`, `password`, `role`, `idEmployee`. Password hashed with BCrypt; roles granted according to `role` (see README, Security). If the username exists, returns the existing user. |
| POST | `/api/role/save` | Creates a role. Body `{"name": "ADMIN"}`. 201 response. |
| POST | `/api/role/addtouser` | `RoleToUserForm` body (`username`, `roleName`). See the issues below: as written, the method replaces the user's role list with `null`. |
| POST | `/api/user/addemloyeetouser` | `EmployeeToUserForm` body (user ID, employee ID): links an employee to a user. |
| DELETE | `/api/user/deletebyEid/{ide}` | Deletes the user linked to employee `{ide}` (called by botRH). |

## botRH — `/RH`

### Employees — `/RH/employees`

| Method | Path | Purpose |
|---|---|---|
| GET | `/RH/employees` | All employees. |
| GET | `/RH/employees/getemployeebyId/{id}` | Employee by ID, with their job (`job`) read from businessMS. |
| GET | `/RH/employees/getemployeesbyjob/{id}`<br>`/RH/employees/active/getemployeesbyjob/{id}`<br>`/RH/employees/archived/getemployeesbyjob/{id}` | Employees of job `{id}`: all, active, archived. |
| GET | `/RH/employees/getemloyeebycompany/{cId}`<br>`/RH/employees/active/getemloyeebycompany/{cId}`<br>`/RH/employees/archived/getemloyeebycompany/{cId}` | Employees of company `{cId}`: all, active, archived. |
| GET | `/RH/employees/getemloyeebyproject/{pId}`<br>`/RH/employees/active/getemloyeebyproject/{pId}`<br>`/RH/employees/archived/getemloyeebyproject/{pId}` | Employees assigned to project `{pId}`: all, active, archived. |
| POST | `/RH/employees/newEmployee` | Creates an employee (`EmployeeRequestDto` body), then their securityMs account: username = `professionalMail`, password = `firstName`, role = `role`. Empty body if an account already exists for this e-mail address. |
| PUT | `/RH/employees/update/{id}` | `EmployeeRequestDto` body. Returns the employee built from the body without saving it (see the issues below). |
| PUT | `/RH/employees/leavescalculator/{id}` | Recomputes the leave balance: 1.8 days per month elapsed since the start of the active contract. |
| PUT | `/RH/employees/addJob/{eId}/{jId}` | Assigns job `{jId}` (businessMS) to employee `{eId}`. |
| PUT | `/RH/employees/addproject/{employeeId}/{projectId}` | Assigns the employee to the project. |
| DELETE | `/RH/employees/archive/{id}`<br>`/RH/employees/noarchive/{id}` | Archives / unarchives the employee. |
| DELETE | `/RH/employees/delete/{id}` | Deletes the employee, then their securityMs account. |

### Contracts — `/RH/contracts`

| Method | Path | Purpose |
|---|---|---|
| GET | `/RH/contracts` | All contracts. |
| GET | `/RH/contracts/{id}` | Contract by ID. |
| GET | `/RH/contracts/byEmployer/{id}`<br>`/RH/contracts/active/byEmployer/{id}`<br>`/RH/contracts/archived/byEmployer/{id}` | Contracts where employee `{id}` is the employer (`employer`): all, active, archived. |
| GET | `/RH/contracts/byEmplyee/{id}`<br>`/RH/contracts/active/byEmplyee/{id}`<br>`/RH/contracts/archived/byEmplyee/{id}` | Contracts of employee `{id}`: all, active, archived. |
| GET | `/RH/contracts/employeeFormContract/{id}` | Employee holding the contract. |
| POST | `/RH/contracts/newcontract` | Creates a contract (`ContractRequestDto` body). CIVP and internship: gross salary = net salary; CIVP: `ONE_YEAR` duration; CDI: `UNLIMITED` duration. |
| PUT | `/RH/contracts/addtoEmployer/{idContract}/{idEmployer}` | Sets the contract's employer. |
| PUT | `/RH/contracts/addtoEmployee/{idEmployee}/{idContract}` | Assigns the contract to the employee (employee first in the path). |
| PUT | `/RH/contracts/update/{id}` | Replaces the contract with the `ContractRequestDto` body. |
| DELETE | `/RH/contracts/archive/{id}`<br>`/RH/contracts/noarchive/{id}` | Archives / unarchives. |
| DELETE | `/RH/contracts/delete/{id}` | Deletes. |

### Clauses — `/RH/clauses`

| Method | Path | Purpose |
|---|---|---|
| GET | `/RH/clauses` | All clauses. |
| GET | `/RH/clauses/{id}` | Clause by ID. |
| GET | `/RH/clauses/comapny/{id}`<br>`/RH/clauses/active/comapny/{id}`<br>`/RH/clauses/archived/comapny/{id}` | Clauses written by employees of company `{id}`: all, active, archived. |
| GET | `/RH/clauses/contract/{id}` | Clauses of the contract. |
| GET | `/RH/clauses/active/contract/{id}`<br>`/RH/clauses/archived/contract/{id}` | Not implemented: empty response. |
| GET | `/RH/clauses/writer/{id}`<br>`/RH/clauses/active/writer/{id}`<br>`/RH/clauses/archived/writer/{id}` | Clauses written by employee `{id}`: all, active, archived. |
| POST | `/RH/clauses/addclause` | Creates a clause (`ClauseRequestDto` body), dated today. |
| PUT | `/RH/clauses/update/{id}` | Replaces the clause with the `ClauseRequestDto` body. |
| PUT | `/RH/clauses/addtocontract/{idClause}/{idContract}` | Adds the clause to the contract. |
| PUT | `/RH/clauses/removefromcontract/{idClause}/{idContract}` | Removes the clause from the contract. |
| PUT | `/RH/clauses/addtowriter/{idClause}/{idWriter}` | Sets the clause's writer. |
| DELETE | `/RH/clauses/archive/{id}`<br>`/RH/clauses/noarchive/{id}` | Archives / unarchives. |
| DELETE | `/RH/clauses/delete/{id}` | Deletes. |

### Leave — `/RH/holidays`

| Method | Path | Purpose |
|---|---|---|
| GET | `/RH/holidays` | All leave requests. |
| GET | `/RH/holidays/byId/{id}` | Request by ID. |
| GET | `/RH/holidays/byemployee/{id}`<br>`/RH/holidays/active/byemployee/{id}`<br>`/RH/holidays/archived/byemployee/{id}` | Requests of the employee: all, active, archived. |
| POST | `/RH/holidays/newHoliday/{idEmployee}` | Creates a request (`HoldayRequestDto` body: `startDate`, `duration` among `HALF_DAY`, `ONE_DAY`, `TWO_DAYS`, `THREE_DAYS`). Recomputes the employee's balance; empty body if the balance is too low, otherwise `PENDING` or `REFUSED` status (see [architecture](ARCHITECTURE.en.md#leave-requests)). |
| PUT | `/RH/holidays/holidaytoemployee/{idHolday}/{idEmployee}` | Links the request to an employee. |
| PUT | `/RH/holidays/upadte/{id}` | Replaces the request with the `HoldayRequestDto` body. |
| DELETE | `/RH/holidays/prouve/{id}` | Approves the request (`PROVED` status). |
| DELETE | `/RH/holidays/refuse/{id}` | Meant to refuse; as written, also sets the `PROVED` status (see the issues below). |
| DELETE | `/RH/holidays/archive/{id}`<br>`/RH/holidays/noarchive/{id}` | Archives / unarchives. |
| DELETE | `/RH/holidays/delete/{id}` | Deletes. |

### Projects — `/RH/projects`

| Method | Path | Purpose |
|---|---|---|
| GET | `/RH/projects` | All projects. |
| GET | `/RH/projects/{id}` | Project by ID. |
| GET | `/RH/projects/byemployee/{id}`<br>`/RH/projects/active/byemployee/{id}`<br>`/RH/projects/archived/byemployee/{id}` | Projects of the employee: all, active, archived. |
| GET | `/RH/projects/byCompany/{id}`<br>`/RH/projects/active/ByCompany/{id}`<br>`/RH/projects/archived/ByCompany/{id}` | Projects of the company (`idCompany`): all, active, archived. |
| POST | `/RH/projects/newProject` | Creates a project (`ProjectRequestDto` body). |
| PUT | `/RH/projects/addtoemployee/{projectId}/{employeeId}` | Empty method: has no effect. Use `PUT /RH/employees/addproject/{employeeId}/{projectId}`. |
| PUT | `/RH/projects/update/{id}` | Not implemented: empty response. |
| DELETE | `/RH/projects/archive/{id}`<br>`/RH/projects/noarchive/{id}` | Archives / unarchives. |
| DELETE | `/RH/projects/delete/{id}` | Deletes. |

### Documents — `/RH/documents`

| Method | Path | Purpose |
|---|---|---|
| GET | `/RH/documents` | All documents, content included (`data`, Base64-encoded in the JSON). |
| GET | `/RH/documents/documentbyid/{id}` | Document by ID. |
| GET | `/RH/documents/byemployee/{id}`<br>`/RH/documents/active/byemployee/{id}`<br>`/RH/documents/archived/byemployee/{id}` | Documents of the employee: all, active, archived. |
| GET | `/RH/documents/activeproimg/byemployee/{id}`<br>`/RH/documents/archivedproimg/byemployee/{id}` | Active / archived profile pictures (`PROFIL_IMG`) of the employee. |
| POST | `/RH/documents/newDocument` | Creates a document record (`DocumentRequestDto` body), without a file. |
| PUT | `/RH/documents/uploadDocument/{id}` | Uploads the file of record `{id}`: multipart, `file` field, 200 MB at most. Returns the record with `downloadUrl`. |
| GET | `/RH/documents/download/{id}` | File content, with its MIME type. |
| PUT | `/RH/documents/addToEmployee/{documentId}/{employeeId}` | Links the document to an employee. |
| PUT | `/RH/documents/addTPost/{documentId}/{postId}` | Links the document to a post and gives it the `POST_IMG` type. |
| PUT | `/RH/documents/update/{id}` | Replaces the record with the `DocumentRequestDto` body. |
| DELETE | `/RH/documents/archive/{id}`<br>`/RH/documents/noarchive/{id}` | Archives / unarchives. |
| DELETE | `/RH/documents/delete/{id}` | Deletes. |

### Posts — `/RH/post`

| Method | Path | Purpose |
|---|---|---|
| GET | `/RH/post/{idc}` | Posts by employees of company `{idc}`. |
| GET | `/RH/post/active/{idc}`<br>`/RH/post/archived/{idc}` | Active / archived posts of the company, newest first. |
| GET | `/RH/post/active/e/{ide}` | All posts of employee `{ide}`. |
| GET | `/RH/post/archived/e/{ide}` | **Active** posts of the employee. |
| GET | `/RH/post/e/{ide}` | **Archived** posts of the employee. |
| GET | `/RH/post/employeebypost/{idp}` | Author of the post. |
| POST | `/RH/post/newpost` | Creates a post (`PostRequestDto` body), dated today. |
| PUT | `/RH/post/addToEmployee/{idPost}/{idEmployee}` | Sets the author. |
| PUT | `/RH/post/update/{id}` | No body: saves the post again unchanged. |
| DELETE | `/RH/post/archivepost/{idp}`<br>`/RH/post/noarchivepost/{idp}` | Archives / unarchives; returns the post. |
| DELETE | `/RH/post/deletepost/{idp}` | Deletes. |

### Comments — `/RH/comment`

| Method | Path | Purpose |
|---|---|---|
| GET | `/RH/comment` | All comments. |
| GET | `/RH/comment/{id}` | Comment by ID. |
| GET | `/RH/comment/post/{idp}`<br>`/RH/comment/active/post/{idp}`<br>`/RH/comment/archived/post/{idp}` | Comments of the post: all, active, archived. |
| GET | `/RH/comment/getwriter/{id}` | Author of the comment. |
| POST | `/RH/comment/newComment` | Creates a comment (`CommentRequestDto` body). |
| PUT | `/RH/comment/update/{id}` | Replaces the comment with the `CommentRequestDto` body. |
| PUT | `/RH/comment/addtoPost/{commentId}/{postId}` | Links the comment to a post. |
| PUT | `/RH/comment/addtoemployee/{commentId}/{employeeId}` | Sets the author. |
| DELETE | `/RH/comment/archive/{id}`<br>`/RH/comment/noarchive/{id}` | Archives / unarchives; returns the comment. |
| DELETE | `/RH/comment/delete/{id}` | Deletes. |

### Notifications — `/RH/notifications`

| Method | Path | Purpose |
|---|---|---|
| GET | `/RH/notifications` | All notifications. |
| GET | `/RH/notifications/getbyId/{id}` | Notification by ID. |
| GET | `/RH/notifications/byemployee/{id}`<br>`/RH/notifications/active/byemployee/{id}`<br>`/RH/notifications/archived/byemployee/{id}` | Notifications addressed to the employee: all, active, archived. |
| POST | `/RH/notifications/newNotification` | Creates a notification (`NotificationRequestDto` body: `text`, `notificationType` among `ALERT`, `INFO`, `IMPORTANT`, `REMEMBER`). |
| PUT | `/RH/notifications/addtoemployee/{idNotification}/{idEmployee}` | Sets the recipient. |
| DELETE | `/RH/notifications/archive/{id}`<br>`/RH/notifications/noarchive/{id}` | Archives / unarchives. |
| DELETE | `/RH/notifications/delete/{id}` | Deletes. |

### Claims — `/RH/Reclamations`

| Method | Path | Purpose |
|---|---|---|
| GET | `/RH/Reclamations` | All claims. |
| GET | `/RH/Reclamations/getbyId/{id}` | Claim by ID. |
| GET | `/RH/Reclamations/getbysource/{senderId}`<br>`/RH/Reclamations/active/getbysource/{senderId}`<br>`/RH/Reclamations/archived/getbysource/{senderId}` | Claims sent by the employee: all, active, archived. |
| GET | `/RH/Reclamations/getbyDestination/{receiverId}`<br>`/RH/Reclamations/active/getbyDestination/{receiverId}`<br>`/RH/Reclamations/archived/getbyDestination/{receiverId}` | Claims received by the employee: all, active, archived. |
| POST | `/RH/Reclamations/newReclamation` | Creates a claim (`ReclamationRequestDto` body). |
| PUT | `/RH/Reclamations/update/{id}` | Replaces the claim with the `ReclamationRequestDto` body. |
| PUT | `/RH/Reclamations/reclamationToSender/{reclamationId}/{senderId}` | Sets the sender. |
| PUT | `/RH/Reclamations/reclamationToReceiver/{reclamationId}/{receiverId}` | Sets the recipient. |
| DELETE | `/RH/Reclamations/archive/{id}`<br>`/RH/Reclamations/noarchive/{id}` | Archives / unarchives. |
| DELETE | `/RH/Reclamations/delete/{id}` | Deletes. |

## businessMS — `/business`

### Companies — `/business/Companies`

| Method | Path | Purpose |
|---|---|---|
| GET | `/business/Companies` | All companies. |
| POST | `/business/Companies/newCompany` | Company sign-up (`CompanyRequestDto` body, including the manager's details): creates the company, the `header_office` office, the `Administration` department, the `Manager` job and the manager (role `ADMIN`) in botRH, then stores their ID in `idManger`. See [architecture](ARCHITECTURE.en.md#company-sign-up). |
| GET | `/business/Companies/getCompanyById/{id}` | Company by ID, with the manager's record read from botRH. |
| GET | `/business/Companies/getCompanyByEmployeeId/{id}` | Company of employee `{id}`, found through their job. |
| GET | `/business/Companies/totalofemployeesbyCompany/{id}` | Not implemented: always returns `1`. |
| GET | `/business/Companies/totalofofficessbyCompany/{id}` | Not implemented: always returns `0`. |
| GET | `/business/Companies/totalofDepartmentsbyCompany/{id}` | Number of departments of the company. |
| PUT | `/business/Companies/update/{id}` | Replaces the company with the `CompanyRequestDto` body. |
| PUT | `/business/Companies/archive/{id}` | Archives the company and, in cascade, its offices, departments and jobs. |
| PUT | `/business/Companies/addManager/{idManager}/{idCompany}` | Sets the manager (`idManger`). |
| DELETE | `/business/Companies/delete/{id}` | Deletes the company. |

### Offices — `/business/offices`

| Method | Path | Purpose |
|---|---|---|
| GET | `/business/offices` | All offices. |
| GET | `/business/offices/byid/{id}` | Office by ID. |
| GET | `/business/offices/byCompany/{id}`<br>`/business/offices/active/byCompany/{id}`<br>`/business/offices/archived/byCompany/{id}` | Offices of the company: all, active, archived. |
| POST | `/business/offices/newOffice` | Creates an office (`OfficeRequestDto` body). |
| PUT | `/business/offices/update/{id}` | Replaces the office with the `OfficeRequestDto` body. |
| PUT | `/business/offices/addToCompany/{idOffice}/{idCompany}` | Links the office to a company. |
| PUT | `/business/offices/archive/{id}` | Archives. |
| DELETE | `/business/offices/delete/{id}` | Deletes. |

### Departments — `/business/Departments`

| Method | Path | Purpose |
|---|---|---|
| GET | `/business/Departments` | All departments. |
| GET | `/business/Departments/{id}` | Department by ID. |
| GET | `/business/Departments/byOffice/{id}`<br>`/business/Departments/active/byOffice/{id}`<br>`/business/Departments/archived/byOffice/{id}` | Departments of the office: all, active, archived. |
| GET | `/business/Departments/getOffice/{id}` | Office of the department. |
| POST | `/business/Departments/newDepartment` | Creates a department (`DepartmentRequestDto` body: `name`). |
| PUT | `/business/Departments/update/{id}` | Replaces the department with the `DepartmentRequestDto` body. |
| PUT | `/business/Departments/addToOffice/{departmentId}/{officeId}` | Links the department to an office. |
| PUT | `/business/Departments/archive/{id}` | Archives. |
| DELETE | `/business/Departments/delete/{id}` | Deletes. |

### Jobs — `/business/jobs`

| Method | Path | Purpose |
|---|---|---|
| GET | `/business/jobs` | All jobs. |
| GET | `/business/jobs/{id}` | Job by ID. |
| GET | `/business/jobs/getdep/{id}` | Department of the job. |
| GET | `/business/jobs/jobsbycompany/{cid}` | Jobs of the company. |
| GET | `/business/jobs/jobsbydepartment/{id}`<br>`/business/jobs/active/jobsbydepartment/{id}`<br>`/business/jobs/archived/jobsbydepartment/{id}` | Jobs of the department: all, active, archived. |
| GET | `/business/jobs/cidbyjob/{jid}` | ID of the job's company. |
| GET | `/business/jobs/companybyjob/{jid}` | Company of the job. |
| GET | `/business/jobs/jobByEmployeeId/{eid}` | Job of the employee (record read from botRH). |
| POST | `/business/jobs/newjob` | Creates a job (`JobRequestDto` body). |
| PUT | `/business/jobs/update/{id}` | Replaces the job with the `JobRequestDto` body. |
| PUT | `/business/jobs/addToDepartement/{jobId}/{departmentId}` | Links the job to a department. |
| PUT | `/business/jobs/archive/{id}` | Archives. |
| DELETE | `/business/jobs/delete/{id}` | Deletes. |

## Known issues

Behaviour found in the code that differs from what the route name suggests:

- `POST /api/role/addtouser` compares strings with `==`: no branch runs and the user's role list is replaced with
  `null`.
- `PUT /RH/employees/update/{id}` does not save the employee.
- `DELETE /RH/holidays/refuse/{id}` sets the `PROVED` status. `prouve` and `refuse` build a notification without saving
  it.
- In `POST /RH/holidays/newHoliday/{idEmployee}`, the `switch` on the duration has no `break`: the duration taken into
  account is always 3 days.
- `GET /RH/post/active/e/{ide}`, `/archived/e/{ide}` and `/e/{ide}` are shifted (see the posts table).
- `PUT /RH/projects/addtoemployee/...` and `PUT /RH/projects/update/{id}` are not implemented, nor are
  `GET /RH/clauses/active/contract/{id}` and `/archived/contract/{id}`.
- `GET /business/Companies/totalofemployeesbyCompany/{id}` and `totalofofficessbyCompany/{id}` return constants.
