import React from 'react'
import { NavLink } from 'react-router-dom';

import './teacher.css'
const menus = [{
	title: 'HOME',
	path: 'home'
},
{
	title: 'STUDENT',
	path: 'student'
},
{
	title: 'EXAM',
	path: 'exam'
},
{
	title: 'CHART',
	path: 'chart'
},
];
const TeacherNavBar = () => {
	return ( 
		<div className="teacher-navbar">
			{menus.map((menu, index) => (
				<div
				  className="teacher-navbar-item" 
				  key = {index}
				>
					<NavLink to={`/teacher/${menu.path}`}>
						<button className="btn nav-btn"	> 
							{menu.title}
						</button>
					</NavLink>
				</div>
				)
			)}
		</div >
	)
}
export default TeacherNavBar