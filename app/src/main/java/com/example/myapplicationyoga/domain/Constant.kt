package com.example.myapplicationyoga.domain


import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest


object Constant {
 val supabase = createSupabaseClient(
  supabaseUrl = "https://wwlfmhzctcsallaxfbdd.supabase.co",
  supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Ind3bGZtaHpjdGNzYWxsYXhmYmRkIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDA5ODk3MTksImV4cCI6MjA1NjU2NTcxOX0.JES_-OXw0qC4oAyObdjkkYzE9fT_bSmBJE0IuZeP4c8"
 ){
  install(Postgrest)
  install(Auth)
 }
}