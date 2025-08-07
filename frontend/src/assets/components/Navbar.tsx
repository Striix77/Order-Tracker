import { Link } from 'react-router-dom';
import './Navbar.css';

function Navbar() {
  return (
    <nav className="navbar">
      <div className="link-container">
        <Link to="/">Home</Link>
        <Link to="/create">Create/Modify</Link>
      </div>
    </nav>
  );
}

export default Navbar;
