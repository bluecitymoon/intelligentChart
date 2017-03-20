(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('NetworkTexiCompany', NetworkTexiCompany);

    NetworkTexiCompany.$inject = ['$resource'];

    function NetworkTexiCompany ($resource) {
        var resourceUrl =  'api/network-texi-companies/:id';

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
