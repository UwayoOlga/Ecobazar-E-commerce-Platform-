const ProductCard = ({ product }) => {
  return (
    <div className="bg-white p-4 rounded shadow">
      <h2 className="text-xl font-semibold">{product.name}</h2>
      <p className="text-gray-600">{product.description}</p>
      <p className="text-green-600 font-bold">${product.price}</p>
    </div>
  );
};

export default ProductCard;
