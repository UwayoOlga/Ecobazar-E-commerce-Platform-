import React, { useEffect, useState } from 'react';
import { API_BASE_URL } from '../api';
import { useAuth } from '../AuthContext';
import ProductForm from './ProductForm';
import { Link } from 'react-router-dom';
import { toast } from 'react-hot-toast';

export default function ProductList() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const { user, token } = useAuth();
  const [showForm, setShowForm] = useState(false);
  const [editProduct, setEditProduct] = useState(null);
  const [formLoading, setFormLoading] = useState(false);

  useEffect(() => {
    async function fetchProducts() {
      setLoading(true);
      setError('');
      try {
        const res = await fetch(`${API_BASE_URL}/api/products`, {
          headers: token ? { Authorization: `Bearer ${token}` } : {},
        });
        if (!res.ok) throw new Error('Failed to fetch products');
        const data = await res.json();
        setProducts(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    }
    fetchProducts();
  }, [token]);

  const handleEditClick = (product) => {
    setShowForm(true);
    setEditProduct(product);
  };

  const handleDeleteClick = async (productId) => {
    if (!window.confirm('Are you sure you want to delete this product?')) return;
    try {
      const res = await fetch(`${API_BASE_URL}/api/products/${productId}`, {
        method: 'DELETE',
        headers: { Authorization: `Bearer ${token}` },
      });
      if (!res.ok) throw new Error('Failed to delete product');
      await fetchProducts();
      toast.success('Product deleted!');
    } catch (err) {
      alert(err.message);
      toast.error(err.message);
    }
  };

  const handleAddClick = () => {
    setShowForm(true);
    setEditProduct(null);
  };

  const handleFormSubmit = async (formData) => {
    setFormLoading(true);
    try {
      if (editProduct) {
        // Edit
        const res = await fetch(`${API_BASE_URL}/api/products/${editProduct.id}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify(formData),
        });
        if (!res.ok) throw new Error('Failed to update product');
        toast.success('Product updated!');
      } else {
        // Add
        const res = await fetch(`${API_BASE_URL}/api/products`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify(formData),
        });
        if (!res.ok) throw new Error('Failed to add product');
        toast.success('Product added!');
      }
      setShowForm(false);
      setEditProduct(null);
      await fetchProducts();
    } catch (err) {
      toast.error(err.message);
      throw err;
    } finally {
      setFormLoading(false);
    }
  };

  return (
    <div className="max-w-6xl mx-auto py-8">
      <h2 className="text-2xl font-bold mb-6 text-center">Products</h2>
      {loading && <div className="text-center">Loading...</div>}
      {error && <div className="text-center text-red-500">{error}</div>}
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
        {products.map(product => (
          <div key={product.id} className="bg-white rounded-lg shadow p-4 flex flex-col">
            <Link to={`/products/${product.id}`} className="hover:opacity-80">
              <img
                src={product.imageUrl || 'https://via.placeholder.com/200x150'}
                alt={product.name}
                className="h-40 w-full object-cover rounded mb-2"
              />
              <h3 className="text-lg font-semibold mb-1">{product.name}</h3>
            </Link>
            <p className="text-gray-500 mb-1">{product.categoryName}</p>
            <p className="font-bold text-blue-600 mb-2">${product.price}</p>
            <p className="text-sm text-gray-600 mb-2">{product.description}</p>
            <p className="text-xs text-gray-400 mb-2">Stock: {product.quantityInStock}</p>
            {user && user.role === 'ADMIN' && (
              <div className="mt-auto flex gap-2">
                <button
                  className="bg-yellow-500 text-white px-3 py-1 rounded hover:bg-yellow-600 transition"
                  onClick={() => handleEditClick(product)}
                >Edit</button>
                <button
                  className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600 transition"
                  onClick={() => handleDeleteClick(product.id)}
                >Delete</button>
              </div>
            )}
          </div>
        ))}
      </div>
      {user && user.role === 'ADMIN' && (
        <div className="flex justify-center mt-8">
          <button
            className="bg-green-600 text-white px-6 py-2 rounded hover:bg-green-700 transition"
            onClick={handleAddClick}
          >Add Product</button>
        </div>
      )}
      {showForm && (
        <div className="fixed inset-0 bg-black bg-opacity-40 flex items-center justify-center z-50">
          <div className="bg-white rounded shadow-lg p-6 relative w-full max-w-lg">
            <ProductForm
              initialValues={editProduct || {}}
              onSubmit={handleFormSubmit}
              onCancel={() => { setShowForm(false); setEditProduct(null); }}
              loading={formLoading}
            />
          </div>
        </div>
      )}
    </div>
  );
}
