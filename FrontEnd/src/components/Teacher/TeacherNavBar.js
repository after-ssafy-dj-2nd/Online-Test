import React, {useState} from 'react'
import { NavLink } from 'react-router-dom';

import './teacher.css'
const menus = [{
	title: 'HOME',
	path: ''
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
		<div className="teacher-side-bar">
			{menus.map((menu, index) => ( 
				<NavLink to={`/teacher/${menu.path}`} exact>
					<button
						className="btn nav-btn"	
					  key = {index}> 
						{menu.title}
					</button>
				</NavLink>)
			)}
		</div >
	)
}
export default TeacherNavBar