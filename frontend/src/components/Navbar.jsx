import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../AuthContext';
import { useCart } from '../CartContext';

export default function Navbar() {
  const { user, logout } = useAuth();
  const { cart } = useCart();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <nav className="bg-white shadow-md py-3 px-6 flex items-center justify-between">
      <Link to="/" className="text-xl font-bold text-blue-700">E-Commerce</Link>
      <div className="flex items-center gap-4">
        <Link to="/cart" className="relative text-blue-600 hover:underline">
          Cart
          {cart.length > 0 && (
            <span className="absolute -top-2 -right-3 bg-green-500 text-white text-xs rounded-full px-2 py-0.5">{cart.length}</span>
          )}
        </Link>
        {user && user.role === 'CUSTOMER' && (
          <Link to="/orders" className="text-blue-600 hover:underline">My Orders</Link>
        )}
        {user && (
          <Link to="/profile" className="text-blue-600 hover:underline">Profile</Link>
        )}
        {user && user.role === 'ADMIN' && (
          <>
            <Link to="/admin/orders" className="text-blue-600 hover:underline">All Orders</Link>
            <Link to="/admin/users" className="text-blue-600 hover:underline">All Users</Link>
            <Link to="/admin" className="text-blue-600 hover:underline">Admin</Link>
          </>
        )}
        {!user && (
          <>
            <Link to="/login" className="text-blue-600 hover:underline">Login</Link>
            <Link to="/register" className="text-blue-600 hover:underline">Register</Link>
          </>
        )}
        {user && (
          <>
            <span className="text-gray-700">{user.email} ({user.role})</span>
            <button
              onClick={handleLogout}
              className="ml-2 bg-blue-500 text-white px-3 py-1 rounded hover:bg-blue-600 transition"
            >
              Logout
            </button>
          </>
        )}
      </div>
    </nav>
  );
} 