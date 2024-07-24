import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import '../css/profile.css'; // Optional: If you want to include custom styles
import { Helmet } from 'react-helmet';

const Profile = () => {
    return (
        <div id='profileBody'>

            <Helmet>
                <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" />
            </Helmet>

            <div className="container bootstrap snippets bootdeys">
                <div className="row" id="user-profile">
                    <div className="col-lg-3 col-md-4 col-sm-4">
                        <div className="main-box clearfix">
                            <h2>John Doe</h2>
                            <div className="profile-status">
                                <i className="fa fa-check-circle"></i> Online
                            </div>
                            <img src="https://bootdey.com/img/Content/avatar/avatar1.png" alt="Profile" className="profile-img img-responsive center-block" />

                            <div className="profile-details">
                                <ul className="fa-ul">
                                    <li><i className="fa-li fa fa-comment"></i>Posts: <span>828</span></li>
                                </ul>
                            </div>

                            <div className="profile-message-btn center-block text-center">
                                <a href="#" className="btn btn-success">
                                    <i className="fa fa-envelope"></i> Send message
                                </a>
                            </div>
                        </div>
                    </div>

                    <div className="col-lg-9 col-md-8 col-sm-8">
                        <div className="main-box clearfix">
                            <div className="profile-header">
                                <h3><span>User info</span></h3>
                                <a href="#" className="btn btn-primary edit-profile">
                                    <i className="fa fa-pencil-square fa-lg"></i> Edit profile
                                </a>
                            </div>

                            <div className="row profile-user-info">
                                <div className="col-sm-8">
                                    <div className="profile-user-details clearfix">
                                        <div className="profile-user-details-label">
                                            First Name
                                        </div>
                                        <div className="profile-user-details-value">
                                            John
                                        </div>
                                    </div>
                                    <div className="profile-user-details clearfix">
                                        <div className="profile-user-details-label">
                                            Last Name
                                        </div>
                                        <div className="profile-user-details-value">
                                            Doe
                                        </div>
                                    </div>
                                    <div className="profile-user-details clearfix">
                                        <div className="profile-user-details-label">
                                            Address
                                        </div>
                                        <div className="profile-user-details-value">
                                            10880 Malibu Point,
                                            <br /> Malibu, Calif., 90265
                                        </div>
                                    </div>
                                    <div className="profile-user-details clearfix">
                                        <div className="profile-user-details-label">
                                            Email
                                        </div>
                                        <div className="profile-user-details-value">
                                            johndoe@bootdey.com
                                        </div>
                                    </div>
                                    <div className="profile-user-details clearfix">
                                        <div className="profile-user-details-label">
                                            Phone number
                                        </div>
                                        <div className="profile-user-details-value">
                                            011 223 344 556 677
                                        </div>
                                    </div>
                                </div>
                                <div className="col-sm-4 profile-social">
                                    <ul className="fa-ul">
                                        <li><i className="fa-li fa fa-twitter-square"></i><a href="#">@scjohansson</a></li>
                                        <li><i className="fa-li fa fa-linkedin-square"></i><a href="#">John Doe</a></li>
                                        <li><i className="fa-li fa fa-facebook-square"></i><a href="#">John Doe</a></li>
                                        <li><i className="fa-li fa fa-skype"></i><a href="#">Black_widow</a></li>
                                        <li><i className="fa-li fa fa-instagram"></i><a href="#">Avenger_Scarlett</a></li>
                                    </ul>
                                </div>
                            </div>

                            <div className="tabs-wrapper profile-tabs">
                                <ul className="nav nav-tabs">
                                    <li className="nav-item">
                                        <a className="nav-link active" href="#tab-posts" data-bs-toggle="tab">posts</a>
                                    </li>
                                    <li className="nav-item">
                                        <a className="nav-link" href="#tab-followers" data-bs-toggle="tab">followers</a>
                                    </li>

                                </ul>

                                <div className="tab-content">
                                    <div className="tab-pane fade show active" id="tab-posts">
                                        <div className="table-responsive">
                                            <table className="table">
                                                <tbody>
                                                    <tr>
                                                        <td className="text-center">
                                                            <i className="fa fa-comment"></i>
                                                        </td>
                                                        <td>
                                                            John Doe posted a comment in <a href="#">Avengers Initiative</a> project.
                                                        </td>
                                                        <td>
                                                            2014/08/08 12:08
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td className="text-center">
                                                            <i className="fa fa-truck"></i>
                                                        </td>
                                                        <td>
                                                            John Doe changed order status from <span className="badge bg-primary">Pending</span> to <span className="badge bg-success">Completed</span>
                                                        </td>
                                                        <td>
                                                            2014/08/08 12:08
                                                        </td>
                                                    </tr>
                                                    {/* Add more posts rows as needed */}
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>

                                    <div className="tab-pane fade" id="tab-followers">
                                        <ul className="widget-users row">
                                            <li className="col-md-6">
                                                <div className="img">
                                                    <img src="https://bootdey.com/img/Content/avatar/avatar1.png" className="img-responsive" alt="Friend" />
                                                </div>
                                                <div className="details">
                                                    <div className="name">
                                                        <a href="#">John Doe</a>
                                                    </div>
                                                    <div className="time">
                                                        <i className="fa fa-clock-o"></i> Last online: 5 minutes ago
                                                    </div>
                                                    <div className="type">
                                                        <span className="badge bg-danger">Admin</span>
                                                    </div>
                                                </div>
                                            </li>
                                            {/* Add more followers list items as needed */}
                                        </ul>
                                        <br />
                                        <a href="#" className="btn btn-success float-end">View all users</a>
                                    </div>

                                    <div className="tab-pane fade" id="tab-chat">
                                        <div className="conversation-wrapper">
                                            <div className="conversation-content">
                                                <div className="conversation-inner" style={{ height: '340px', overflowY: 'scroll' }}>
                                                    <div className="conversation-item item-left clearfix">
                                                        <div className="conversation-user">
                                                            <img src="https://bootdey.com/img/Content/avatar/avatar1.png" className="img-responsive" alt="User" />
                                                        </div>
                                                        <div className="conversation-body">
                                                            <div className="name">
                                                                Ryan Gossling
                                                            </div>
                                                            <div className="time">
                                                                September 21, 2013 18:28
                                                            </div>
                                                            <div className="text">
                                                                I don't think they tried to market it to the billionaire, spelunking, base-jumping crowd.
                                                            </div>
                                                        </div>
                                                    </div>
                                                    {/* Add more chat items as needed */}
                                                </div>
                                            </div>
                                            <div className="conversation-new-message">
                                                <form>
                                                    <div className="form-group">
                                                        <textarea className="form-control" rows="2" placeholder="Enter your message..."></textarea>
                                                    </div>
                                                    <div className="clearfix">
                                                        <button type="submit" className="btn btn-success float-end">Send message</button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Profile;
