
import static org.assertj.core.api.Assertions.assertThat;

import com.motta.insurance_association_service.entity.Association;
import com.motta.insurance_association_service.mapper.AssociationMapper;
import com.motta.insurance_association_service.model.AssociationDTO;
import com.motta.insurance_association_service.repository.AssociationRepository;
import com.motta.insurance_association_service.service.AssociationServiceImplementation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class InsuranceAssociationTest {

    @Mock
    private AssociationRepository associationRepository;

    @InjectMocks
    private AssociationServiceImplementation associationService;


    @DisplayName("JUnit test for getAssociation method")
    @Test
    public void getAssociationByIdTest(){

        Association association = new Association(1,10001, 1);
        // given
        when(associationRepository.findById(association.getId())).thenReturn(Optional.of(association));

        // when
        AssociationDTO savedAssociationDTO = associationService.retrieveAssociationById( (association.getId()) );

        // then
        assertThat(savedAssociationDTO).isNotNull();
    }

    @DisplayName("JUnit test for getAllAssociations method")
    @Test
    public void getAllAssociations(){
        // given - precondition or setup

        Association a1 = new Association(1,10001, 1);
        Association a2 = new Association(2,10002, 1);
        Association a3 = new Association(3,10003, 2);
        Association a4 = new Association(4,10004, 2);
        Association a5 = new Association(5,10005, 3);
        Association a6 = new Association(6,10006, 3);
        when(associationRepository.findAll()).thenReturn(List.of(a1,a2, a3,a4,a5,a6));

        // when -  action or the behaviour that we are going test
        List<AssociationDTO> associationList = associationService.retrieveAllAssociations();

        // then - verify the output
        assertThat(associationList).isNotNull();
        assertThat(associationList.size()).isEqualTo(6);
    }



    // JUnit test for saveEmployee method
    @DisplayName("JUnit test for createAssociation method")
    @Test
    public void createAssociation(){


        // Initial Set up
        AssociationDTO associationDTO = new AssociationDTO(1,10001, 1);
        Association newAssociation = AssociationMapper.mapToAssociation(associationDTO);

        when(associationRepository.save(any())).thenReturn(newAssociation);
        Optional<AssociationDTO> savedAssociation = Optional.ofNullable(associationService.createAssociation(associationDTO));
        assertTrue(savedAssociation.isPresent());

    }
    @DisplayName("JUnit test for updateAssociation method")
    @Test
    public void updateAssociation(){

        // Initial Set up
        AssociationDTO associationDTO = new AssociationDTO(3,10003, 1);
        Association newAssociation = AssociationMapper.mapToAssociation(associationDTO);

        when(associationRepository.save(any())).thenReturn(newAssociation);
        associationDTO.setEmployeeId(10002);
        Optional<AssociationDTO> updatedAssociation = Optional.ofNullable(associationService.updateAssociation(associationDTO));
        assertTrue(updatedAssociation.isPresent());
        assertThat(updatedAssociation.get().getEmployeeId()).isEqualTo(10003);

    }

    @DisplayName("JUnit test for deleteAssociation method")
    @Test
    public void deleteAssociation(){
        // given - precondition or setup
        int associationId = 1;

        doNothing().when(associationRepository).deleteById(associationId);

        // when -  action or the behaviour that we are going test
        associationService.deleteAssociation(associationId);

        // then - verify the output
        verify(associationRepository, times(1)).deleteById(associationId);
    }

}