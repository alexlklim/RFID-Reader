import React, {Component} from 'react';
import '../../styles/component/header.scss';
import logo from '../../styles/img/logo.png';
import { BsFillPersonFill } from 'react-icons/bs';


class Header extends Component {
    render() {
        return (
            <header className="header">
                <img src={logo} alt="logo"/>
                <BsFillPersonFill  className="icon"/>
                
            </header>
        );
    }
}

export default Header;