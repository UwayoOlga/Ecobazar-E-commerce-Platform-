import React, { useEffect, useState } from 'react';
import { useAuth } from '../AuthContext';
import { API_BASE_URL } from '../api';

const ROLE_OPTIONS = ['CUSTOMER', 'ADMIN'];

export default function AdminUsers() {
  const { user, token } = useAuth();
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [updating, setUpdating] = useState(null);

  useEffect(() => {
    async function fetchUsers() {
      setLoading(true);
      setError('');
      try {
        const res = await fetch(`${API_BASE_URL}/api/users`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        if (!res.ok) throw new Error('Failed to fetch users');
        const data = await res.json();
        setUsers(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    }
    if (user && token && user.role === 'ADMIN') fetchUsers();
  }, [user, token]);

  const handleRoleChange = async (userId, newRole) => {
    setUpdating(userId);
    try {
      const res = await fetch(`${API_BASE_URL}/api/users/${userId}/role`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ role: newRole }),
      });
      if (!res.ok) throw new Error('Failed to update role');
      setUsers(users =>
        users.map(u => (u.id === userId ? { ...u, role: newRole } : u))
      );
    } catch (err) {
      alert(err.message);
    } finally {
      setUpdating(null);
    }
  };

  const handleDelete = async (userId) => {
    if (!window.confirm('Are you sure you want to delete this user?')) return;
    setUpdating(userId);
    try {
      const res = await fetch(`${API_BASE_URL}/api/users/${userId}`, {
        method: 'DELETE',
        headers: { Authorization: `Bearer ${token}` },
      });
      if (!res.ok) throw new Error('Failed to delete user');
      setUsers(users => users.filter(u => u.id !== userId));
    } catch (err) {
      alert(err.message);
    } finally {
      setUpdating(null);
    }
  };

  if (!user || user.role !== 'ADMIN') {
    return <div className="text-center py-8">Admins only.</div>;
  }

  return (
    <div className="max-w-4xl mx-auto py-10 px-4">
      <h2 className="text-2xl font-bold mb-6 text-center">All Users</h2>
      {loading && <div className="text-center">Loading...</div>}
      {error && <div className="text-center text-red-500">{error}</div>}
      {users.length === 0 && !loading ? (
        <div className="text-center text-gray-500">No users found.</div>
      ) : (
        <table className="w-full bg-white rounded shadow">
          <thead>
            <tr className="bg-gray-100">
              <th className="py-2 px-4 text-left">ID</th>
              <th className="py-2 px-4 text-left">Name</th>
              <th className="py-2 px-4 text-left">Email</th>
              <th className="py-2 px-4 text-left">Role</th>
              <th className="py-2 px-4 text-left">Actions</th>
            </tr>
          </thead>
          <tbody>
            {users.map(u => (
              <tr key={u.id} className="border-b last:border-b-0">
                <td className="py-2 px-4">{u.id}</td>
                <td className="py-2 px-4">{u.fullName}</td>
                <td className="py-2 px-4">{u.email}</td>
                <td className="py-2 px-4">
                  <select
                    value={u.role}
                    onChange={e => handleRoleChange(u.id, e.target.value)}
                    disabled={updating === u.id}
                    className="px-2 py-1 border rounded"
                  >
                    {ROLE_OPTIONS.map(role => (
                      <option key={role} value={role}>{role}</option>
                    ))}
                  </select>
                </td>
                <td className="py-2 px-4">
                  <button
                    className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600 transition"
                    onClick={() => handleDelete(u.id)}
                    disabled={updating === u.id}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
} 