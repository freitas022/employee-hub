package com.freitas.employeehub.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.freitas.employeehub.dto.EmployeeDTO;
import com.freitas.employeehub.entity.EmployeeModel;
import com.freitas.employeehub.exception.NotFoundException;
import com.freitas.employeehub.repository.EmployeeRepository;

import dto.EmployeeDTOBuilder;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService service;

    @Mock
    private EmployeeRepository repository;

    private static final LocalDateTime now = LocalDateTime.now();

    @DisplayName("When calling the findById method it must return the object with the selected id")
    @Test
    void testFindById() {
        // given
        Long id = 1234L;
        EmployeeModel expected = new EmployeeModel(id, "Alex", "alex@example.com", "password", now, now);
        // when
        when(repository.findById(expected.getId())).thenReturn(Optional.of(expected));
        EmployeeDTO actual = service.findById(expected.getId());
        // then
        assertThat(actual.getId(), is(equalTo(expected.getId())));
    }

    @DisplayName("When calling the findById method it should throw an exception")
    @Test
    void testFindById_NotFound() {
        // given
        Long id = 1234L;
        when(repository.findById(id)).thenReturn(Optional.empty());
        // when
        assertThrows(NotFoundException.class, () -> service.findById(id));
        // then
        verify(repository, times(1)).findById(id);
    }

    @DisplayName("When calling the insert method it must return the saved object")
    @Test
    void testInsert() {
        // given
        EmployeeDTO dto = EmployeeDTOBuilder.builder().build().toDto();
        EmployeeModel expected = new EmployeeModel();
        service.toDtoFromEntity(dto, expected);
        // when
        when(repository.save(any(EmployeeModel.class))).thenReturn(expected);

        EmployeeDTO actual = service.insert(dto);
        // then
        assertThat(actual.getId(), is(equalTo(expected.getId())));
        assertThat(actual.getCreatedAt(), is(equalTo(expected.getCreatedAt())));
    }

    @DisplayName("When calling the method toDtoFromEntity it must map an entity to DTO")
    @Test
    void testToDtoFromEntity() {
        // given
        EmployeeDTO expected = EmployeeDTOBuilder.builder().build().toDto();
        EmployeeModel actual = new EmployeeModel();
        // when
        service.toDtoFromEntity(expected, actual);
        // then
        assertThat(actual.getName(), is(equalTo("Alex")));
        assertThat(actual.getId(), is(equalTo(12345L)));
        assertThat(actual.getCreatedAt(), is(equalTo(expected.getCreatedAt())));
    }

    @DisplayName("When calling the update method it must return an updated object")
    @Test
    void testUpdate() {
        // given
        EmployeeDTO dto = EmployeeDTOBuilder.builder().build().toDto();
        EmployeeModel expected = new EmployeeModel();
        service.toDtoFromEntity(dto, expected);
        // when
        when(repository.findById(expected.getId())).thenReturn(Optional.of(expected));
        when(repository.save(any(EmployeeModel.class))).thenReturn(expected);

        EmployeeDTO actual = service.update(expected.getId(), dto);
        // then
        assertThat(actual.getId(), is(equalTo(12345L)));
        assertThat(actual.getName(), is(equalTo("Alex")));
    }

    @DisplayName("When calling the update method it should throw an exception")
    @Test
    void testUpdate_NotFound() {
        // given
    	Long id = 235L;
        EmployeeModel model = new EmployeeModel(25L,"Jhony", "jhony@example.com", "password", now, now);
        // when
        when(repository.findById(anyLong())).thenReturn(Optional.of(model));
        // then
        EmployeeDTO dto = EmployeeDTOBuilder.builder().build().toDto();
        assertThrows(NotFoundException.class, () -> service.update(id, dto));
        verify(repository).save(any());
        verify(repository).findById(anyLong());
    }
    
    @DisplayName("Should delete a employee")
    @Test
    void testDelete() {
        // given
    	EmployeeDTO dto = EmployeeDTOBuilder.builder().build().toDto();
    	EmployeeModel expected = new EmployeeModel();
        service.toDtoFromEntity(dto, expected);
        // when
        doNothing().when(repository).delete(any());
        when(repository.findById(anyLong())).thenReturn(Optional.of(expected));
        // then
        service.delete(dto.getId());
        verify(repository).findById(anyLong());
        verify(repository).delete(any());
    }
    
    @DisplayName("Should return empty when object not found")
    @Test
    void testDelete_NotFound() {
        // given
    	EmployeeDTO dto = EmployeeDTOBuilder.builder().build().toDto();
    	EmployeeModel expected = new EmployeeModel();
        service.toDtoFromEntity(dto, expected);
        // when
        doThrow(new NotFoundException(1L)).when(repository).delete(any());
        when(repository.findById(anyLong())).thenReturn(Optional.of(expected));
        // then
        assertThrows(NotFoundException.class, () -> service.delete(1L));
        verify(repository).findById(anyLong());
        verify(repository).delete(any());
    }
    
    @DisplayName("Should return a list of objects with pagination")
    @Test
    void testFindAllPageable() {
        //given
        EmployeeModel expected = new EmployeeModel(25L,"Jhony", "jhony@example.com", "password", now, now);
    	Page<EmployeeModel> pagination = new PageImpl<>(Collections.singletonList(expected));
        // when
        when(repository.findAll(any(PageRequest.class))).thenReturn(pagination);
        List<EmployeeDTO> actual = service.findAll(0, 5);
        // then
        assertEquals(1, actual.size());
        verify(repository).findAll(any(PageRequest.class));
    }
}
