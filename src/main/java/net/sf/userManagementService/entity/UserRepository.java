package net.sf.userManagementService.entity;

import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface UserRepository extends ReactiveSortingRepository<User,String> {
}
