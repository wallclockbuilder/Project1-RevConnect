import React, { useState, useEffect } from 'react';
import { User } from '../interface/types';
import "../css/Follow.css";
import { useAuth } from '../context/AuthContext';

const sampleFollowersArray: User[] = [
    { userId: 7, username: 'dwight_schrute', email: 'dwight@example.com', firstName: 'Dwight', lastName: 'Schrute' },
    { userId: 8, username: 'angela_martin', email: 'angela@example.com', firstName: 'Angela', lastName: 'Martin' },
    { userId: 9, username: 'kevin_malone', email: 'kevin@example.com', firstName: 'Kevin', lastName: 'Malone' }
];

const Followers: React.FC = () => {
    const [followers, setFollowers] = useState<User[]>(sampleFollowersArray);
    const { user, token } = useAuth();

    useEffect(() => {
        const fetchFollowers = async () => {
            try {
                const url = `http://localhost:9090/api/users/${user.userId}/followers`;
                const response = await fetch(url, {
                    method: "GET",
                    credentials: 'include',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`
                    }
                });

                if (response.ok) {
                    const contentType = response.headers.get("Content-Type");
                    if (contentType && contentType.includes("application/json")) {
                        const data = await response.json();
                        if (Array.isArray(data)) {
                            setFollowers(data);
                        } else {
                            console.error("Fetched data is not an array:", data);
                        }
                    } else {
                        console.error("Expected JSON response but got", contentType);
                    }
                } else {
                    console.error("Failed to fetch followers", response.statusText);
                }
            } catch (error) {
                console.error("Failed to fetch followers:", error);
            }
        };

        fetchFollowers();
    }, [user.userId, token]);


    useEffect(() => {
        setFollowers(sampleFollowersArray);
    }, []);

    console.log("Followers in Followers component:", followers);

    return (
        <div className="container mt-4">
            {Array.isArray(followers) && followers.length === 0 ? (
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
