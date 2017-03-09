(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PersonIncome', PersonIncome);

    PersonIncome.$inject = ['$resource'];

    function PersonIncome ($resource) {
        var resourceUrl =  'api/person-incomes/:id';

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
                url: 'api/person-incomes/person/:id',
                params: { id : '@id'}
            }
        });
    }
})();
