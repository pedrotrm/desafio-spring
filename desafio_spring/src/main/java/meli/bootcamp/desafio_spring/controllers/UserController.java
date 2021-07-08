package meli.bootcamp.desafio_spring.controllers;

import meli.bootcamp.desafio_spring.dtos.FollowerCountDTO;
import meli.bootcamp.desafio_spring.dtos.FollowersDTO;
import meli.bootcamp.desafio_spring.dtos.FollowingDTO;
import meli.bootcamp.desafio_spring.exceptions.ResourceNotFoundException;
import meli.bootcamp.desafio_spring.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{userId}/followers/count")
    public FollowerCountDTO getFollowersCount(@PathVariable(value = "userId") Long sellerId){
        FollowerCountDTO followerCountDTO = null;
        try{
            followerCountDTO = userService.getFollowersCount(sellerId);
        }catch (ResourceNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
        }
        return followerCountDTO;
    }

    @GetMapping("{userId}/followers/list")
    public FollowersDTO getFollowers(@PathVariable(value = "userId") Long sellerId){
        FollowersDTO followersDTO = null;
        try{
            followersDTO = userService.getFollowers(sellerId);
        }catch (ResourceNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
        }
        return followersDTO;
    }

    @GetMapping("/{userId}/followed/list")
    public FollowingDTO getFollowing(@PathVariable Long userId){
        FollowingDTO followingDTO = null;
        try{
            followingDTO = userService.getFollowing(userId);
        }catch (ResourceNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
        }
        return followingDTO;
    }

    @PostMapping("{userId}/follow/{userIdToFollow}")
    @ResponseStatus(HttpStatus.OK)
    public void followSeller(@PathVariable Long userId, @PathVariable(value = "userIdToFollow") Long sellerId){
        try{
            this.userService.followSeller(userId,sellerId);
        }catch (ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }
    }
}
