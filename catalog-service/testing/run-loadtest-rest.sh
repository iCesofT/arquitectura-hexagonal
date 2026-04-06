#!/bin/bash

# Load Test Quick Runner
# Usage: ./run-loadtest.sh [profile]
# Profiles: smoke, base, light, moderate, aggressive, very-aggressive

set -e

#PROFILE=${1:-base}
PROFILE=$1

echo "🚀 Starting k6 load test - Profile: $PROFILE"
echo ""

case $PROFILE in
  smoke)
    echo "⚡ Smoke Test (10 seconds)"
    SHORT_RUN=true k6 run loadtest.js
    ;;
  base)
    echo "📊 Base Test (5 minutes, pico 600 rps) - RECOMENDADO"
    k6 run loadtest.js
    ;;
  light)
    echo "💡 Light Test (5 minutes, pico 300 rps)"
    TARGET_RATE_1=100 TARGET_RATE_2=150 TARGET_RATE_3=250 TARGET_RATE_4=300 \
    PREALLOCATED_VUS=50 MAX_VUS=150 \
    k6 run loadtest-rest.js
    ;;
  moderate)
    echo "📈 Moderate Test (5 minutes, pico 800 rps)"
    TARGET_RATE_1=200 TARGET_RATE_2=400 TARGET_RATE_3=650 TARGET_RATE_4=800 \
    PREALLOCATED_VUS=150 MAX_VUS=600 \
    k6 run loadtest-rest.js
    ;;
  aggressive)
    echo "⚔️  Aggressive Test (5 minutes, pico 1000 rps)"
    TARGET_RATE_1=300 TARGET_RATE_2=600 TARGET_RATE_3=800 TARGET_RATE_4=1000 \
    PREALLOCATED_VUS=200 MAX_VUS=800 \
    k6 run loadtest-rest.js
    ;;
  very-aggressive)
    echo "🔥 Very Aggressive Test (5 minutes, pico 1500 rps)"
    TARGET_RATE_1=500 TARGET_RATE_2=1000 TARGET_RATE_3=1250 TARGET_RATE_4=1500 \
    PREALLOCATED_VUS=300 MAX_VUS=1200 \
    k6 run loadtest-rest.js
    ;;
  *)
    echo "❌ Unknown profile: $PROFILE"
    echo ""
    echo "Available profiles:"
    echo "  smoke              - Quick verification (10 seconds)"
    echo "  base               - Base test (5 min, 600 rps) - RECOMMENDED"
    echo "  light              - Light test (5 min, 300 rps)"
    echo "  moderate           - Moderate test (5 min, 800 rps)"
    echo "  aggressive         - Aggressive test (5 min, 1000 rps)"
    echo "  very-aggressive    - Very aggressive test (5 min, 1500 rps)"
    exit 1
    ;;
esac

echo ""
echo "✅ Test completed!"

