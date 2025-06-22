import React, { useEffect, useState } from 'react';
import { useAuth } from '../AuthContext';
import { API_BASE_URL } from '../api';
import { Link } from 'react-router-dom';

export default function OrderHistory() {
  const { user, token } = useAuth();
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    async function fetchOrders() {
      setLoading(true);
      setError('');
      try {
        const res = await fetch(`${API_BASE_URL}/api/orders/user/${user.id}`, {
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
    if (user && token) fetchOrders();
  }, [user, token]);

  if (!user) return <div className="text-center py-8">Please log in to view your orders.</div>;

  return (
    <div className="max-w-3xl mx-auto py-10 px-4">
      <h2 className="text-2xl font-bold mb-6 text-center">Order History</h2>
      {loading && <div className="text-center">Loading...</div>}
      {error && <div className="text-center text-red-500">{error}</div>}
      {orders.length === 0 && !loading ? (
        <div className="text-center text-gray-500">No orders found.</div>
      ) : (
        <div className="space-y-6">
          {orders.map(order => (
            <Link to={`/orders/${order.id}`} key={order.id} className="block bg-white rounded shadow p-4 hover:bg-blue-50 transition">
              <div className="flex justify-between mb-2">
                <div>
                  <span className="font-semibold">Order #{order.id}</span>
                  <span className="ml-4 text-gray-500 text-sm">{new Date(order.orderDate).toLocaleString()}</span>
                </div>
                <span className="text-blue-600 font-semibold">{order.status}</span>
              </div>
              <div className="mb-2">
                {order.items.map(item => (
                  <div key={item.productId} className="flex justify-between text-sm py-1 border-b last:border-b-0">
                    <span>{item.productName} x {item.quantity}</span>
                    <span>${(item.price * item.quantity).toFixed(2)}</span>
                  </div>
                ))}
              </div>
              <div className="text-right font-bold">Total: ${order.totalAmount.toFixed(2)}</div>
            </Link>
          ))}
        </div>
      )}
    </div>
  );
} 