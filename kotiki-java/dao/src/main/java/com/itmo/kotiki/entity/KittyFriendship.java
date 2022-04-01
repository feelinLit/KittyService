package com.itmo.kotiki.entity;

import javax.persistence.*;

@Entity
@Table(name = "kitty_friendship")
public class KittyFriendship extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "kitty_id")
    private Kitty kitty;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "kittys_friend_id")
    private Kitty kittysFriend;

    protected KittyFriendship() {
    }

    public KittyFriendship(Kitty kitty, Kitty kittysFriend) {
        this.kitty = kitty;
        this.kittysFriend = kittysFriend;
    }

    public Kitty getKittysFriend() {
        return kittysFriend;
    }

    public void setKittysFriend(Kitty kittysFriend) {
        this.kittysFriend = kittysFriend;
    }

    public Kitty getKitty() {
        return kitty;
    }

    public void setKitty(Kitty kitty) {
        this.kitty = kitty;
    }

    public Long getId() {
        return super.getId();
    }


}