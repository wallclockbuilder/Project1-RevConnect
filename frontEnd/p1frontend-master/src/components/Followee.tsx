import React, { useState, useEffect } from 'react';
import { User } from '../interface/types';
import "../css/Follow.css";
import { useAuth } from '../context/AuthContext';

interface FolloweeProps {
    followees: User[]
}   
const Followee: React.FC<FolloweeProps> = ({followees}) => {
    const { user, token } = useAuth();

    console.log("Followees in Followee component:", followees);

    return (
        <div className="container mt-4">
            {Array.isArray(followees) && followees.length === 0 ? (
                <div>You are not following anyone yet.</div>
            ) : (
                <div className="list-group">
                    {followees.map((followee) => (
                        <div key={followee.userId} className="list-group-item d-flex justify-content-between align-items-center">
                            <div>
                                <h5>{followee.firstName} {followee.lastName}</h5>
                                <p>{followee.email}</p>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default Followee;
