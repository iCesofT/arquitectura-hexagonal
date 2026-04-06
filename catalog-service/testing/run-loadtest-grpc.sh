#!/bin/bash

# Load test gRPC configuration
SHORT_RUN="${SHORT_RUN:-false}"
TARGET_RATE_1="${TARGET_RATE_1:-150}"
TARGET_RATE_2="${TARGET_RATE_2:-300}"
TARGET_RATE_3="${TARGET_RATE_3:-500}"
TARGET_RATE_4="${TARGET_RATE_4:-600}"
START_RATE="${START_RATE:-50}"
PREALLOCATED_VUS="${PREALLOCATED_VUS:-100}"
MAX_VUS="${MAX_VUS:-400}"
PAGE_SIZE="${PAGE_SIZE:-50}"
SLEEP_BETWEEN_PAGES_MS="${SLEEP_BETWEEN_PAGES_MS:-0}"
GRPC_HOST="${GRPC_HOST:-localhost:9090}"
LANG="${LANG:-es}"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}🚀 Starting gRPC Load Test${NC}"
echo -e "${YELLOW}Configuration:${NC}"
echo -e "  gRPC Host: ${GRPC_HOST}"
echo -e "  Short Run: ${SHORT_RUN}"
echo -e "  Page Size: ${PAGE_SIZE}"
echo -e "  Language: ${LANG}"
echo -e "  Max VUs: ${MAX_VUS}"
echo -e "  Sleep between pages: ${SLEEP_BETWEEN_PAGES_MS}ms"
echo ""

# Check if k6 is installed
if ! command -v k6 &> /dev/null; then
    echo -e "${RED}❌ k6 is not installed. Please install k6 first.${NC}"
    echo "Visit: https://k6.io/docs/getting-started/installation/"
    exit 1
fi

# Check if loadtest-grpc.js exists
if [ ! -f "loadtest-grpc.js" ]; then
    echo -e "${RED}❌ loadtest-grpc.js not found in current directory${NC}"
    exit 1
fi

# Check if gRPC server is running
echo -e "${YELLOW}🔍 Checking if gRPC server is accessible...${NC}"
if ! nc -z localhost 9090 2>/dev/null; then
    echo -e "${RED}⚠️  Warning: Cannot connect to gRPC server at localhost:9090${NC}"
    echo -e "${YELLOW}   Make sure the application is running with gRPC enabled${NC}"
    echo ""
fi

# Export variables for k6
export SHORT_RUN
export TARGET_RATE_1
export TARGET_RATE_2
export TARGET_RATE_3
export TARGET_RATE_4
export START_RATE
export PREALLOCATED_VUS
export MAX_VUS
export PAGE_SIZE
export SLEEP_BETWEEN_PAGES_MS
export GRPC_HOST
export LANG

echo -e "${BLUE}⚡ Running gRPC load test...${NC}"
echo ""

# Run k6 test
k6 run loadtest-grpc.js

if [ $? -eq 0 ]; then
    echo ""
    echo -e "${GREEN}✅ Load test completed successfully${NC}"
else
    echo ""
    echo -e "${RED}❌ Load test failed${NC}"
    exit 1
fi