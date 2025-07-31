package in.revnita.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.revnita.entity.CitizenPlan;

public interface CitizenPlanRepository extends JpaRepository<CitizenPlan, Integer> {
	@Query("select distinct(planName) from CitizenPlan")//HQL Query
	public List<String>getPlanNames();
	@Query("SELECT DISTINCT c.planStatus FROM CitizenPlan c WHERE c.planStatus IS NOT NULL")
	public List<String> getPlanStatus();
	
}
