package com.ditriot.repo;

import com.ditriot.model.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticRepo extends JpaRepository<Statistic,Long> {
}
