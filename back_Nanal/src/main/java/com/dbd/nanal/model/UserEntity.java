package com.dbd.nanal.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import org.springframework.security.core.GrantedAuthority;
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
    @Column(name = "user_idx")
    private int userIdx;

    @Column(name = "name")
    private String name;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "last_access_date")
    private LocalDateTime lastAccessDate;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "user_id", unique = true)
    private String userId;

    @Column(name = "password")
    private String password;

    @Column(name = "social_code")
    private int socialCode;

    private String role;


    // 연결관계

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserProfileEntity userProfile;

    @OneToOne
    @JoinColumn(name = "emotion_idx")
    private EmotionEntity emotion;

    @OneToMany(mappedBy = "user")
    private List<GroupUserRelationEntity> groupUserRelations = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<NoticeEntity> notices = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ScrapEntity> scraps = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<DiaryCommentEntity> diaryComments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<DiaryEntity> diaries = new ArrayList<>();

    // 스프링 시큐리티 UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

