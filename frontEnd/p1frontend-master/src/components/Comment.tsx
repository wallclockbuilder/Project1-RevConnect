import React from 'react';
import { Comment as CommentType } from '../types';

interface CommentProps {
    comment: CommentType;
}

const Comment: React.FC<CommentProps> = ({ comment }) => {
    return (
        <div className="comment">
            <p>{comment.content}</p>
            <p><small>{comment.createdAt}</small></p>
        </div>
    );
};

export default Comment;
