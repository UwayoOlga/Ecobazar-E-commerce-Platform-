import React, { useState } from 'react';
import { useCart } from '../CartContext';
import { useAuth } from '../AuthContext';
import { API_BASE_URL } from '../api';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-hot-toast';

export default function Checkout() {
  // ... existing code ...
  const handlePlaceOrder = async () => {
    setLoading(true);
    setError('');
    try {
      // ... existing code ...
      if (!res.ok) throw new Error('Failed to place order');
      setSuccess(true);
      toast.success('Order placed!');
      clearCart();
      setTimeout(() => navigate('/'), 2000);
    } catch (err) {
      setError(err.message);
      toast.error(err.message);
    } finally {
      setLoading(false);
    }
  };
  // ... existing code ...
} 