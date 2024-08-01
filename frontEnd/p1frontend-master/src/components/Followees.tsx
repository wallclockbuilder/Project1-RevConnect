import React, { useState, useEffect } from 'react';
import { User } from '../interface/types';
import "../css/Follow.css";
import { useAuth } from '../context/AuthContext';

interface followedUserProps {
    followedUsers: User[],
    loading: boolean
}   
const Followee: React.FC<followedUserProps> = ({followedUsers, loading}) => {
    const { user, token } = useAuth();

    //filter out any invalid entries that are not user objects
    const validFollowedUsers = followedUsers.filter(
        (followedUser): followedUser is User => typeof followedUser === 'object' 
    && followedUser !== null && followedUser.hasOwnProperty('userId')
    );
    console.log("Valid FollowedUsers in Followee component:", validFollowedUsers);

    return (
        <div className="container mt-4">
            {loading ? (
                <p>Loading followed users...</p>
                ) : Array.isArray(followedUsers) && followedUsers.length === 0 ? (
                    <div>You have not followed anyone yet.</div>
            ) : (
                <div className="list-group">
                    {validFollowedUsers.map((followedUser) => (
                        followedUser && (
                        <div key={followedUser.userId} className="list-group-item d-flex justify-content-between align-items-center">
                            <div>
                                <h5>{followedUser.firstName} {followedUser.lastName}</h5>
                                <p>{followedUser.email}</p>
                            </div>
                        </div>
                    )))}
                </div>
            )}
        </div>
    );
};

export default Followee;
