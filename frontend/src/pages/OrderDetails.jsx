import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useAuth } from '../AuthContext';
import { API_BASE_URL } from '../api';

export default function OrderDetails() {
  const { orderId } = useParams();
  const { user, token } = useAuth();
  const [order, setOrder] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    async function fetchOrder() {
      setLoading(true);
      setError('');
      try {
        const res = await fetch(`${API_BASE_URL}/api/orders/${orderId}`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        if (!res.ok) throw new Error('Failed to fetch order');
        const data = await res.json();
        setOrder(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    }
    if (user && token) fetchOrder();
  }, [orderId, user, token]);

  if (!user) return <div className="text-center py-8">Please log in to view order details.</div>;
  if (loading) return <div className="text-center py-8">Loading...</div>;
  if (error) return <div className="text-center text-red-500 py-8">{error}</div>;
  if (!order) return null;

  return (
    <div className="max-w-2xl mx-auto py-10 px-4">
      <button
        className="mb-4 text-blue-600 hover:underline text-sm"
        onClick={() => navigate(-1)}
      >
        &larr; Back to Orders
      </button>
      <div className="bg-white rounded shadow p-6">
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
      </div>
    </div>
  );
} 