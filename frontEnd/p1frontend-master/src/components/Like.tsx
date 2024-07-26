import React from 'react';
import { Like as LikeType } from '../types';

interface LikeProps {
    postId: number;
    likes: LikeType[];
}

const Like: React.FC<LikeProps> = ({ postId, likes }) => {
    const likeCount = likes.filter(like => like.postId === postId).length;

    return (
        <div className="like">
            <button type="button">Like</button>
            <span>{likeCount} likes</span>
        </div>
    );
};

export default Like;
