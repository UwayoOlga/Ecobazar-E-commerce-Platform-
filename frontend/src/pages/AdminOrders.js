import React, { useEffect, useState } from 'react';
import { useAuth } from '../AuthContext';
import { API_BASE_URL } from '../api';
import { Link } from 'react-router-dom';
import { toast } from 'react-hot-toast';

const STATUS_OPTIONS = ['PENDING', 'SHIPPED', 'DELIVERED'];

export default function AdminOrders() {
  const { user, token } = useAuth();
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [updating, setUpdating] = useState(null);

  useEffect(() => {
    async function fetchOrders() {
      setLoading(true);
      setError('');
      try {
        const res = await fetch(`${API_BASE_URL}/api/orders`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        if (!res.ok) throw new Error('Failed to fetch orders');
        const data = await res.json();
        setOrders(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    }
    if (user && token && user.role === 'ADMIN') fetchOrders();
  }, [user, token]);

  const handleStatusChange = async (orderId, newStatus) => {
    setUpdating(orderId);
    try {
      const res = await fetch(`${API_BASE_URL}/api/orders/${orderId}/status`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ status: newStatus }),
      });
      if (!res.ok) throw new Error('Failed to update status');
      setOrders(orders =>
        orders.map(order =>
          order.id === orderId ? { ...order, status: newStatus } : order
        )
      );
      toast.success('Order status updated!');
    } catch (err) {
      alert(err.message);
      toast.error(err.message);
    } finally {
      setUpdating(null);
    }
  };

  if (!user || user.role !== 'ADMIN') {
    return <div className="text-center py-8">Admins only.</div>;
  }

  return (
    <div className="max-w-5xl mx-auto py-10 px-4">
      <h2 className="text-2xl font-bold mb-6 text-center">All Orders</h2>
      {loading && <div className="text-center">Loading...</div>}
      {error && <div className="text-center text-red-500">{error}</div>}
      {orders.length === 0 && !loading ? (
        <div className="text-center text-gray-500">No orders found.</div>
      ) : (
        <div className="space-y-6">
          {orders.map(order => (
            <div key={order.id} className="bg-white rounded shadow p-4">
              <div className="flex justify-between mb-2">
                <div>
                  <span className="font-semibold">Order #{order.id}</span>
                  <span className="ml-4 text-gray-500 text-sm">{new Date(order.orderDate).toLocaleString()}</span>
                  <span className="ml-4 text-gray-700 text-sm">User ID: {order.userId}</span>
                </div>
                <div className="flex items-center gap-2">
                  <span className="text-blue-600 font-semibold">{order.status}</span>
                  <select
                    value={order.status}
                    onChange={e => handleStatusChange(order.id, e.target.value)}
                    disabled={updating === order.id}
                    className="ml-2 px-2 py-1 border rounded"
                  >
                    {STATUS_OPTIONS.map(status => (
                      <option key={status} value={status}>{status}</option>
                    ))}
                  </select>
                </div>
              </div>
              <div className="mb-2">
                {order.items.map(item => (
                  <div key={item.productId} className="flex justify-between text-sm py-1 border-b last:border-b-0">
                    <span>{item.productName} x {item.quantity}</span>
                    <span>${(item.price * item.quantity).toFixed(2)}</span>
                  </div>
                ))}
              </div>
              <div className="flex justify-between items-center">
                <div className="text-right font-bold">Total: ${order.totalAmount.toFixed(2)}</div>
                <Link to={`/orders/${order.id}`} className="text-blue-600 hover:underline text-sm">View Details</Link>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
} 