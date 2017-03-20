(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('CreditCardActivityType', CreditCardActivityType);

    CreditCardActivityType.$inject = ['$resource'];

    function CreditCardActivityType ($resource) {
        var resourceUrl =  'api/credit-card-activity-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
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
