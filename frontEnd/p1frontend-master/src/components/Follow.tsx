import React, { useState, useEffect } from 'react';
import { User, FollowDTO } from '../interface/types';

interface FollowProps {
    usersArray: User[],
}

const sampleUsersArray: User[] = [
    { userId: 1, username: 'john_doe', email: 'john@example.com', firstName: 'John', lastName: 'Doe' },
    { userId: 2, username: 'jane_smith', email: 'jane@example.com', firstName: 'Jane', lastName: 'Smith' },
    { userId: 3, username: 'alice_jones', email: 'alice@example.com', firstName: 'Alice', lastName: 'Jones' }
];

const Follow: React.FC<FollowProps> = ({ usersArray = sampleUsersArray }) => {
    const [localUsers, setLocalUsers] = useState<User[]>(usersArray);

    useEffect(() => {
        setLocalUsers(usersArray);
    }, [usersArray]);

    const handleFollow = async (followerId: number, followeeId: number) => {
        try {
            const followDTO: FollowDTO = {
                followerId: followerId,
                followeeId: followeeId,
            };
            const url = `http://localhost:9090/api/users/${followerId}/followers`;
            const req = {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(followDTO)
            };

            const response = await fetch(url, req);

            if (response.ok) {
                setLocalUsers(localUsers.filter((user: User) => user.userId !== followeeId));
                console.log(`Followed user with userId: ${followeeId}`);
            } else {
                console.error("Failed to follow user", response.statusText);
            }
        } catch (error) {
            console.error("Failed to follow user:", error);
        }
    };

    console.log("Users prop in Follow component:", localUsers);

    return (
        <div className="container mt-4">
            {Array.isArray(localUsers) && localUsers.length === 0 ? (
                <div>No users found to follow. Invite a friend to sign up and they will show up here so you can follow them.</div>
            ) : (
                <div className="list-group">
                    {localUsers.map((user) => (
                        <div key={user.userId} className="list-group-item d-flex justify-content-between align-items-center">
                            <div>
                                <h5>{user.firstName} {user.lastName}</h5>
                                <p>{user.email}</p>
                            </div>
                            <button className="btn btn-primary" onClick={() => handleFollow(user.userId, user.userId)}>
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
