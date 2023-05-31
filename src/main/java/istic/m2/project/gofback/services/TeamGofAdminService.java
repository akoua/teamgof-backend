package istic.m2.project.gofback.services;

import istic.m2.project.gofback.controllers.dto.TeamGofAdminLoginInDto;
import istic.m2.project.gofback.entities.TeamGofAdmin;
import istic.m2.project.gofback.entities.enums.RoleType;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.exceptions.MessageError;
import istic.m2.project.gofback.repositories.TeamGofAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamGofAdminService {

    private final TeamGofAdminRepository teamGofAdminRepository;
    private final PasswordEncoder passwordEncoder;

    public Long createAdmin(TeamGofAdminLoginInDto dto) {
        return teamGofAdminRepository.save(new TeamGofAdmin()
                .withEmail(dto.getEmail())
                .withPwd(passwordEncoder.encode(dto.getPwd()))
                .withRole(RoleType.ADMIN)).getId();
    }

    public Boolean deleteAdmin(Long idAdmin) throws BusinessException {
        try {
            teamGofAdminRepository.deleteById(idAdmin);
            return true;
        } catch (Exception e) {
            throw new BusinessException(MessageError.ERROR_DATABASE, String.format("delete admin with id %s", idAdmin));
        }
    }
}
