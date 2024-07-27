import React from 'react';
import { Post as PostType, Comment as CommentType, Like as LikeType } from '../interface/types';
import Comment from './Comment';
import Like from './Like';

interface PostProps {
    post: PostType;
    comments: CommentType[];
    likes: LikeType[];
}

const Post: React.FC<PostProps> = ({ post, comments, likes }) => {
    return (
        <div className="post">
            <h2>{post.content}</h2>
            <p><small>{post.createdAt}</small></p>
            <Like postId={post.postId} likes={likes} />
            <div className="comments">
                {comments.map((comment) => (
                    <Comment key={comment.commentId} comment={comment} />
                ))}
            </div>
        </div>
    );
};

export default Post;
