export function formatNumber(amount) {
  // Use toLocaleString to add thousand separators
  if (amount === "" || amount === null) return 0;
  return amount
    .toLocaleString("en-US", { style: "decimal", minimumFractionDigits: 0, maximumFractionDigits: 0 })
    .replace(/,/g, ".");
}
