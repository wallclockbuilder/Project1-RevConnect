import React, { useState, useEffect } from 'react';
import { User, FollowDTO } from '../interface/types';
import "../css/Follow.css";
import { useAuth } from '../context/AuthContext';

interface FollowProps {
    onFollow: (followerUserId: number, followeeUserId: number) => void
}

const sampleUsersArray: User[] = [
    { userId: 1, username: 'john_doe', email: 'john@example.com', firstName: 'John', lastName: 'Doe' },
    { userId: 2, username: 'jane_smith', email: 'jane@example.com', firstName: 'Jane', lastName: 'Smith' },
    { userId: 3, username: 'alice_jones', email: 'alice@example.com', firstName: 'Alice', lastName: 'Jones' }
];

const Follow: React.FC<FollowProps> = ({ onFollow }: FollowProps) => {
    const [users, setUsers] = useState<User[]>(sampleUsersArray);
    const {user, token} = useAuth();

    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const req = {
                    method: "GET",
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`
                    }
                };
                const url = `http://localhost:9090/api/users`;
                const response = await fetch(url, req);
                console.log("Response:", response);
                if (response.ok) {
                    const contentType = response.headers.get("Content-Type");
                    if (contentType && contentType.includes("application/json")) {
                        const data = await response.json();
                        console.log("Fetched data:", data);
                        if (Array.isArray(data)) {
                            setUsers(data);
                        } else {
                            console.error("Fetched data is not an array:", data);
                        }
                    } else {
                        console.error("Expected JSON response but got", contentType);
                    }
                } else {
                    console.error("Failed to fetch users", response.statusText);
                }
            } catch (error) {
                console.error("Failed to fetch users:", error);
            }
        };

        fetchUsers();
    }, [token]);

    

    console.log("Users prop in Follow component:", users);

    return (
        <div className="container mt-4">
            {Array.isArray(users) && users.length === 0 ? (
                <div>No users found to follow. Invite a friend to sign up and they will show up here so you can follow them.</div>
            ) : (
                <div className="list-group">
                    {users.map((userN) => (
                        <div key={userN.userId} className="list-group-item d-flex justify-content-between align-items-center">
                            <div>
                                <h5>{userN.firstName} {userN.lastName}</h5>
                                <p>{userN.email}</p>
                            </div>
                            <button className="btn btn-primary folllow-btn" onClick={() => onFollow(userN.userId, user.userId)}>
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
