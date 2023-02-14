package com.dbd.nanal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@Getter @Setter
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx", columnDefinition = "INT UNSIGNED")
    private int userIdx;

    @Column(name = "name")
    private String name;

    @Column(name = "creation_date")
    @CreationTimestamp
    private LocalDateTime creationDate;

    @Column(name = "last_access_date")
    private LocalDateTime lastAccessDate;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "user_id", unique = true)
    private String userId;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "social_code")
    private int socialCode;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();


    // 연결관계

    @OneToOne(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true)
//    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private UserProfileEntity userProfile;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "emotion_idx")
    @JsonIgnore
    private EmotionEntity emotion;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<GroupUserRelationEntity> groupUserRelations = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<NoticeEntity> notices = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<ScrapEntity> scraps = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<DiaryCommentEntity> diaryComments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<DiaryEntity> diaries = new ArrayList<>();

    @OneToMany(mappedBy = "user_idx1", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<FriendEntity> friendEntity;

    @OneToMany(mappedBy = "user_idx2", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<FriendEntity> friendEntity2;

    // 스프링 시큐리티 UserDetails

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

    }

    // 스프링 시큐리티에서 사용하는 회원 구분 id
    @Override
    @JsonIgnore
    public String getUsername() {
        return this.userId;
    }

    // 계정 만료되었는지 여부
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠겼는지 확인
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }
    
    // 아래는 미사용 -> 전체 true 반환
    // 계정 비밀번호 만료되었는지 확인
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 사용가능한 상태인지 확인
    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

}

