(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PersonFanDisbribution', PersonFanDisbribution);

    PersonFanDisbribution.$inject = ['$resource'];

    function PersonFanDisbribution ($resource) {
        var resourceUrl =  'api/person-fan-disbributions/:id';

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
            'update': { method:'PUT' },
            'loadAllByPersonId' : {
                method: 'GET',
                isArray: true,
                url: 'api/person-fan-disbributions/person/:id',
                params: { id : '@id'}
            }
        });
    }
})();
