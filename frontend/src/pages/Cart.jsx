import React from 'react';
import { useCart } from '../CartContext';
import { Link } from 'react-router-dom';
import { toast } from 'react-hot-toast';

export default function Cart() {
  const { cart, updateQuantity, removeFromCart, clearCart } = useCart();

  const total = cart.reduce((sum, item) => sum + item.price * item.quantity, 0);

  if (cart.length === 0) {
    return (
      <div className="text-center py-10">
        <h2 className="text-2xl font-bold mb-4">Your Cart is Empty</h2>
        <Link to="/" className="text-blue-600 hover:underline">Continue Shopping</Link>
      </div>
    );
  }

  return (
    <div className="max-w-4xl mx-auto py-8">
      <h2 className="text-2xl font-bold mb-6">Your Cart</h2>
      <div className="bg-white shadow rounded-lg border border-gray-100">
        <ul className="divide-y divide-gray-200">
          {cart.map(item => (
            <li key={item.id} className="p-4 flex items-center justify-between">
              <div className="flex items-center">
                <img 
                  src={item.imageUrl || 'https://via.placeholder.com/100'} 
                  alt={item.name}
                  className="h-16 w-16 object-cover rounded border shadow mr-4 bg-white"
                />
                <div>
                  <h3 className="font-semibold">{item.name}</h3>
                  <p className="text-gray-600">${item.price.toFixed(2)}</p>
                </div>
              </div>
              <div className="flex items-center gap-4">
                <div className="flex items-center border rounded">
                  <button 
                    onClick={() => updateQuantity(item.id, item.quantity - 1)}
                    className="px-3 py-1 text-lg"
                    disabled={item.quantity <= 1}
                  >-</button>
                  <span className="px-4 py-1">{item.quantity}</span>
                  <button 
                    onClick={() => updateQuantity(item.id, item.quantity + 1)}
                    className="px-3 py-1 text-lg"
                  >+</button>
                </div>
                <p className="font-semibold w-20 text-right">${(item.price * item.quantity).toFixed(2)}</p>
                <button 
                  onClick={() => removeFromCart(item.id)}
                  className="text-red-500 hover:text-red-700"
                >Remove</button>
              </div>
            </li>
          ))}
        </ul>
      </div>
      <div className="mt-6 flex justify-between items-center">
        <button
          onClick={() => {
            if(window.confirm('Are you sure you want to clear the cart?')) {
              clearCart();
              toast.success('Cart cleared!');
            }
          }}
          className="bg-gray-200 text-gray-700 px-4 py-2 rounded hover:bg-gray-300"
        >Clear Cart</button>
        <div className="text-right">
          <p className="text-xl font-bold">Total: ${total.toFixed(2)}</p>
          <Link to="/checkout">
            <button className="mt-2 bg-green-600 text-white px-6 py-2 rounded hover:bg-green-700">
              Proceed to Checkout
            </button>
          </Link>
        </div>
      </div>
    </div>
  );
} 