import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const ProductCard = ({ product, fetchProducts }) => {
  const navigate = useNavigate();

  const deleteProduct = async () => {
    await axios.delete(`http://localhost:8080/api/products/${product.id}`);
    fetchProducts();
  };

  return (
    <div className="bg-white p-4 rounded-xl shadow-lg hover:shadow-2xl transition-shadow duration-300 flex flex-col group cursor-pointer border border-gray-100 hover:border-blue-400">
      <div className="relative w-full h-48 mb-3 overflow-hidden rounded-lg bg-gray-50 flex items-center justify-center">
        <img
          src={product.imageUrl || 'https://via.placeholder.com/300x200'}
          alt={product.name}
          className="object-cover w-full h-full group-hover:scale-105 transition-transform duration-300"
        />
      </div>
      <h2 className="text-lg font-bold mb-1 truncate" title={product.name}>{product.name}</h2>
      <p className="text-gray-600 text-sm mb-1 line-clamp-2">{product.description}</p>
      <p className="text-green-600 font-semibold mb-2">${product.price}</p>
      <div className="flex gap-2 mt-auto">
        <button
          onClick={() => navigate(`/edit/${product.id}`)}
          className="px-3 py-1 bg-blue-500 text-white rounded hover:bg-blue-600 transition"
        >
          Edit
        </button>
        <button
          onClick={deleteProduct}
          className="px-3 py-1 bg-red-500 text-white rounded hover:bg-red-600 transition"
        >
          Delete
        </button>
      </div>
    </div>
  );
};

export default ProductCard;
