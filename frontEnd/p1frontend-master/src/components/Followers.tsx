import React, { useState, useEffect } from 'react';
import { User } from '../interface/types';
import "../css/Follow.css";
import { useAuth } from '../context/AuthContext';

const sampleFollowersArray: User[] = [
    { userId: 7, username: 'dwight_schrute', email: 'dwight@example.com', firstName: 'Dwight', lastName: 'Schrute', active: true , admin: false},
    { userId: 8, username: 'angela_martin', email: 'angela@example.com', firstName: 'Angela', lastName: 'Martin', active: true , admin: false},
    { userId: 9, username: 'kevin_malone', email: 'kevin@example.com', firstName: 'Kevin', lastName: 'Malone' , active: true , admin: false},
];

interface FollowersProps {
    loading: boolean
    followers: User[]
}

const Followers: React.FC<FollowersProps> = ({loading, followers}: FollowersProps) => {
    const { user, token } = useAuth();

    console.log("Followers in Followers component:", followers);

    return (
        <div className="container mt-4">
            {loading ? (
                <p>Loading followers...</p>
            ) : 
            Array.isArray(followers) && followers.length === 0 ? (
                <div>No one is following you yet.</div>
            ) : (
                <div className="list-group">
                    {followers.map((follower) => (
                        <div key={follower.userId} className="list-group-item d-flex justify-content-between align-items-center">
                            <div>
                                <h5>{follower.firstName} {follower.lastName}</h5>
                                <p>{follower.email}</p>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default Followers;
