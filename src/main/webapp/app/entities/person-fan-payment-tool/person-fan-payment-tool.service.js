(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PersonFanPaymentTool', PersonFanPaymentTool);

    PersonFanPaymentTool.$inject = ['$resource'];

    function PersonFanPaymentTool ($resource) {
        var resourceUrl =  'api/person-fan-payment-tools/:id';
        var findByPersonUrl =  'api/person-fan-payment-tools/person/:id';
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
