package com.greenfield.sdk.gf_sdk.api.bucket

import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema

@JsonSerializableSchema
data class CreateBucketApprovalResponse(
    val creator: String,
    val bucket_name: String,
    val visibility: String, // Assuming VisibilityType is an enum, replace String with the actual enum type
    val payment_address: String,
    val primary_sp_address: String,
    val primary_sp_approval: PrimarySpApproval,
    val charged_read_quota: String
)

@JsonSerializableSchema
data class PrimarySpApproval(
    val expired_height: String,
    val sig: String,
    val global_virtual_group_family_id: Int
)