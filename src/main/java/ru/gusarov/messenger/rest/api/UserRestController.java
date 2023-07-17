package ru.gusarov.messenger.rest.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.gusarov.messenger.util.dto.authentication.MessageResponse;
import ru.gusarov.messenger.util.dto.errors.logic.ErrorCode;
import ru.gusarov.messenger.util.dto.user.UpdatedUsernameDTO;
import ru.gusarov.messenger.models.User;
import ru.gusarov.messenger.services.UserService;
import ru.gusarov.messenger.util.exceptions.user.UsernameOccupiedException;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @GetMapping("{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        return ResponseEntity.ok(userService.convertToUserDTO(
                userService.findByUsername(username))
        );
    }

    @PatchMapping("/changeUsername")
    public ResponseEntity<?> changeUsername(
            @RequestBody UpdatedUsernameDTO updatedUsernameDTO,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if(userService.existByUsername(updatedUsernameDTO.getUsername())) {
            throw UsernameOccupiedException.builder()
                    .errorCode(ErrorCode.USERNAME_IS_OCCUPIED)
                    .errorDate(LocalDateTime.now())
                    .dataCausedError(updatedUsernameDTO)
                    .errorMessage("User with username = " + updatedUsernameDTO.getUsername() + " already exist")
                    .build();
        }

        User user = userService.findByUsername(userDetails.getUsername());
        user.setUsername(updatedUsernameDTO.getUsername());
        userService.save(user);

        return ResponseEntity.ok(new MessageResponse("Name was successfully changed"));
    }
    //При необходимости методы для email и date_of_birth и password
    @PreAuthorize("hasAnyAuthority('ban_users')")
    @PatchMapping("{username}/ban")
    public ResponseEntity<?> banUser(@PathVariable String username) {
        userService.changeEnabled(username);
        return ResponseEntity.ok(new MessageResponse("User was successfully banned"));
    }

    @PreAuthorize("hasAnyAuthority('ban_users')")
    @PatchMapping("{username}/unban")
    public ResponseEntity<?> unbanUser(@PathVariable String username) {
        userService.changeEnabled(username);
        return ResponseEntity.ok(new MessageResponse("User was successfully unbanned"));
    }
}
