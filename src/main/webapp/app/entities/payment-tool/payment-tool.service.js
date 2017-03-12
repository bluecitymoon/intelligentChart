(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PaymentTool', PaymentTool);

    PaymentTool.$inject = ['$resource'];

    function PaymentTool ($resource) {
        var resourceUrl =  'api/payment-tools/:id';
        var findByPersonUrl =  'api/payment-tools/person/:id';
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'loadAllByPersonId' : {
                method: 'GET',
                isArray: true,
                url: findByPersonUrl,
                params: { id : '@id'}
            },
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
