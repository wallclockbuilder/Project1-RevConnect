//types for each entity

export interface User {
    userId: number;
    username: string;
    email: string;
    firstName?: string;
    lastName?: string;
    bio?: string;
    active?: boolean;
}

export interface Post {
    postId: number;
    userId: number;
    username: string;
    content: string;
    createdAt: string;
    updatedAt: string;
    user: User;
}

export interface Comment {
    commentId: number;
    postId: number;
    userId: number;
    content: string;
    createdAt: string;
    updatedAt: string;
}

export interface Like {
    likeId: number;
    postId: number;
    userId: number;
    createdAt: string;
}

export interface Chat {
    chatId: number;
    senderId: number;
    receiverId: number;
    senderUsername: string;
    receiverUsername: string;
    message: string;
    createdAt: string;
}

export interface FollowDTO {
    followerId: number;
    followeeId: number;
}