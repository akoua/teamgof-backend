package istic.m2.project.gofback.services;

import istic.m2.project.gofback.entities.Cavalier;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.exceptions.MessageError;
import istic.m2.project.gofback.repositories.CavalierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CavalierService {

    private final CavalierRepository cavalierRepository;

    public Cavalier findUserById(Long id) throws BusinessException {

        Cavalier cavalier = cavalierRepository.findById(id)
                .orElseThrow(() -> new BusinessException(MessageError.CAVALIER_NOT_FOUND, String.format(" with id %s", id)));
        return cavalier;
    }
}
