import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { API_BASE_URL } from '../api';
import { useAuth } from '../AuthContext';
import { useCart } from '../CartContext';
import { toast } from 'react-hot-toast';

export default function ProductDetails() {
  const { id } = useParams();
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [quantity, setQuantity] = useState(1);
  const [added, setAdded] = useState(false);
  const { user } = useAuth();
  const { addToCart } = useCart();
  const navigate = useNavigate();

  useEffect(() => {
    async function fetchProduct() {
      setLoading(true);
      setError('');
      try {
        const res = await fetch(`${API_BASE_URL}/api/products/${id}`);
        if (!res.ok) throw new Error('Product not found');
        const data = await res.json();
        setProduct(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    }
    fetchProduct();
  }, [id]);

  const handleAddToCart = () => {
    addToCart(product, quantity);
    setAdded(true);
    toast.success('Added to cart!');
    setTimeout(() => setAdded(false), 1500);
  };

  if (loading) return <div className="text-center py-8">Loading...</div>;
  if (error) return <div className="text-center text-red-500 py-8">{error}</div>;
  if (!product) return null;

  return (
    <div className="max-w-3xl mx-auto py-10 px-4">
      <div className="flex flex-col md:flex-row gap-8 bg-white rounded-lg shadow p-6 border border-gray-100">
        <img
          src={product.imageUrl || 'https://via.placeholder.com/300x200'}
          alt={product.name}
          className="w-full md:w-80 h-60 object-cover rounded-lg border shadow-md transition-transform duration-300 hover:scale-105 bg-white"
        />
        <div className="flex-1 flex flex-col">
          <h2 className="text-2xl font-bold mb-2 text-gray-800">{product.name}</h2>
          <p className="text-gray-500 mb-1">{product.categoryName}</p>
          <p className="font-bold text-blue-600 mb-2 text-lg">${product.price}</p>
          <p className="mb-4 text-gray-700">{product.description}</p>
          <p className="text-xs text-gray-400 mb-2">Stock: {product.quantityInStock}</p>
          {user && user.role === 'CUSTOMER' && (
            <div className="flex items-center gap-2 mt-2">
              <input
                type="number"
                min="1"
                max={product.quantityInStock}
                value={quantity}
                onChange={e => setQuantity(Math.max(1, Math.min(product.quantityInStock, Number(e.target.value))))}
                className="w-20 px-2 py-1 border rounded"
              />
              <button
                className="bg-green-600 text-white px-6 py-2 rounded hover:bg-green-700 transition"
                onClick={handleAddToCart}
                disabled={quantity > product.quantityInStock}
              >
                Add to Cart
              </button>
              {added && <span className="text-green-600 ml-2">Added!</span>}
            </div>
          )}
          <button
            className="mt-4 text-blue-600 hover:underline text-sm self-start"
            onClick={() => navigate(-1)}
          >
            &larr; Back to Products
          </button>
        </div>
      </div>
    </div>
  );
} 