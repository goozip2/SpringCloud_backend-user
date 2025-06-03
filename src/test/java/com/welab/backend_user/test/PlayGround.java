package com.welab.backend_user.test;


import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
class UserEntity {
    Long id;
    String userId;
    String phoneNumber;
    boolean child;
}

@Getter
@Setter
class UserDto {
    String userId;
    String phoneNumber;

    public static UserDto fromEntity(UserEntity userEntity) {
        UserDto dto = new UserDto();
        dto.setUserId(userEntity.getUserId());
        dto.setPhoneNumber(userEntity.getPhoneNumber());
        return dto;
    }
}

public class PlayGround {

    @Test
    public void notChildUser() {
        // child가 아닌 사람만 뽑아서 출력
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setUserId("user1");
        user1.setPhoneNumber("010-0000-0000");
        user1.setChild(false);

        UserEntity user2 = new UserEntity();
        user2.setId(2L);
        user2.setUserId("user2");
        user2.setPhoneNumber("010-1111-0000");
        user2.setChild(true);

        List<UserEntity> users = Arrays.asList(user1, user2);

        List<UserDto> dtos = users.stream()
                .filter(u -> !u.child)
                //.map(u -> UserDto.fromEntity(u))
                .map(UserDto::fromEntity)
                .toList();

        List<Integer> numbers1 = List.of(1, 2, 3, 4, 5, 6);
        Optional<Integer> sum = numbers1.stream()
                .reduce((x, y) -> x + y);
    }


    @Test
    public void test() {
        List<Integer> nums = List.of(1,2,3,4,5,6);
        List<Integer> evenNums = nums
                .stream()
                .filter(n -> n%2==0)
                .map(n -> n*2)
                .toList();

        // forEach(): Iterable 또는 Stream의 최종 연산
        // 메소드 참조 문법: x -> System.out.println(x)
        // 콜론 2개: 인수 자동 매핑
        evenNums.forEach(System.out::println);
    }
    @Test
    public void test2() {
        List<Integer> nums = List.of(1,2,3,4,5,6);
        List<Integer> evenNums = new ArrayList<>();

        for(int n: nums) {
            if(n%2==0) {
                evenNums.add(n*2);
            }
        }
        for(int n: evenNums) {
            System.out.println(n);
        }
    }


}
