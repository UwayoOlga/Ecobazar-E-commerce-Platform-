import React, { useEffect, useState } from 'react';
import { API_BASE_URL } from '../api';
import { useAuth } from '../AuthContext';
import ProductForm from './ProductForm';
import { Link } from 'react-router-dom';
import { toast } from 'react-hot-toast';
import ProductCard from './ProductCard';

export default function ProductList() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const { user, token } = useAuth();
  const [showForm, setShowForm] = useState(false);
  const [editProduct, setEditProduct] = useState(null);
  const [formLoading, setFormLoading] = useState(false);

  const fetchProducts = async () => {
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
  };

  useEffect(() => {
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
          <ProductCard key={product.id} product={product} fetchProducts={fetchProducts} />
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
