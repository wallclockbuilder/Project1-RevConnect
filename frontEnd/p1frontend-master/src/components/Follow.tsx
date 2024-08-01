import React, { useState, useEffect } from 'react';
import { User, FollowDTO } from '../interface/types';
import "../css/Follow.css";
import { useAuth } from '../context/AuthContext';

interface FollowProps {
    onFollow: (followerUserId: number, followeeUserId: number) => void,
    usersToFollow: User[],
    loading: boolean
}

const Follow: React.FC<FollowProps> = ({ onFollow, usersToFollow, loading }: FollowProps) => {
    const {user, token} = useAuth();

    console.log("UsersToFollow prop in Follow component:", usersToFollow);

    return (
        <div className="container mt-4">
            {loading ? (
                <p>Loading users to follow...</p>
            ) :  Array.isArray(usersToFollow) && usersToFollow.length === 0 ? (
                <div>No users found to follow. Invite a friend to sign up and they will show up here so you can follow them.</div>
            ) : (
                <div className="list-group">
                    {usersToFollow.map((userN) => (
                        <div key={userN.userId} className="list-group-item d-flex justify-content-between align-items-center">
                            <div>
                                <h5>{userN.firstName} {userN.lastName}</h5>
                                <p>{userN.email}</p>
                            </div>
                            <button className="btn btn-primary folllow-btn" onClick={() => user && onFollow(user.userId, userN.userId)}>
                                Follow
                            </button>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default Follow;
