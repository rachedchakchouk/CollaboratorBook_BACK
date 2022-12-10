package com.ditriot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticYearly {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate statYear;
    private Long allEmployees;
    private Long allCompanies;
    private Long allOffices;
    private Long allDepartments;
    private Long allJobs;
    private Long allPosts;
    private Long allComments;
    private Long allProjects;
    private Long allHolidays;
    private Long allNotifications;
    private Long allClaims;
    private Long allEmployeesByCompany;
    private Long allOfficesByCompany;
    private Long allDepartmentByCompany;
    private Long allJobsByCompany;
    private Long allProjectsByCompany;
    private Long allHolidaysByCompany;
    private Long allPostsByCompany;
    private Long allCommentsByCompany;
    private Long allNotificationsByCompany;
    private Long allClaimsByCompany;
    private Long allDepartmentByOffice;
    private Long allJobsByOffice;
    private Long allEmployeesByOffice;
    private Long allProjectsByOffice;
    private Long allHolidaysByOffice;
    private Long allPostsByOffice;
    private Long allCommentsByOffice;
    private Long allNotificationsByOffice;
    private Long allClaimsByOffice;
    private Long allJobsByDepartment;
    private Long allEmployeesByDepartment;
    private Long allProjectsByDepartment;
    private Long allHolidaysByDepartment;
    private Long allPostsByDepartment;
    private Long allCommentsByDepartment;
    private Long allNotificationsByDepartment;
    private Long allClaimsByDepartment;
    private Long allEmployeesByJob;
    private Long allProjectsByJob;
    private Long allHolidaysByJob;
    private Long allPostsByJob;
    private Long allCommentsByJob;
    private Long allNotificationsByJob;
    private Long allClaimsByJob;
    private Long allProjectsByEmployee;
    private Long allHolidaysByEmployee;
    private Long allPostsByEmployee;
    private Long allCommentsByEmployee;
    private Long allNotificationsByEmployee;
    private Long allClaimsByEmployee;
    private Long allCommentsByPost;
    private Long allArchive;
    private Long allArchivedEmployees;
    private Long allArchivedCompanies;
    private Long allArchivedOffices;
    private Long allArchivedDepartments;
    private Long allArchivedJobs;
    private Long allArchivedPosts;
    private Long allArchivedComments;
    private Long allArchivedProjects;
    private Long allArchivedHolidays;
    private Long allArchivedNotifications;
    private Long allArchivedClaims;
    private Long toDayComments;
    private Long toDayPosts;
}
