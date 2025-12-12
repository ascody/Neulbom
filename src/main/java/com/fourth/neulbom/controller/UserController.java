package com.fourth.neulbom.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fourth.neulbom.dto.*;
import com.fourth.neulbom.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UserCreateResponseDto create(@RequestBody UserCreateRequestDto request){
        return userService.create(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto getUserData(@PathVariable Integer id) throws JsonProcessingException {
        return userService.getUserData(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
        public UserResponseDto update(@PathVariable Integer id,
                                  @RequestBody UserUpdateRequestDto request) throws JsonProcessingException {
        return userService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        userService.delete(id);
    }

    @PatchMapping("{id}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@PathVariable Integer id,
                               @Valid @RequestBody PasswordUpdateRequestDto request) throws JsonProcessingException {
        userService.updatePassword(id, request);
    }


}
