package com.multree.userservice.entity;

public enum RoleUser {

    ADMIN,
    USER,
    SUPERVISOR
//
//    ADMIN(Sets.newHashSet(CATEGORY_READ, CATEGORY_WRITE, PLANS_WRITE, PLANS_READ, MOVIES_READ, MOVIES_WRITE, PRODUCER_READ, PRODUCER_WRITE, CUSTOMER_READ, CUSTOMER_WRITE)),
////    PRODUCER(Sets.newHashSet(CATEGORY_READ, MOVIES_READ, MOVIES_WRITE, PRODUCER_READ, CUSTOMER_WRITE)),
//    USER(Sets.newHashSet(PLANS_READ, MOVIES_READ, CATEGORY_READ));
//
//    private final Set<UserPermission> permission;
//
//    RoleUser(Set<UserPermission> permission) {
//        this.permission = permission;
//    }
//
//    public Set<UserPermission> getPermission() {
//        return permission;
//    }
//
//    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
//        Set<SimpleGrantedAuthority> permissions = getPermission().stream()
//                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
//                .collect(Collectors.toSet());
//
//        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
//        return permissions;
//    }

}
