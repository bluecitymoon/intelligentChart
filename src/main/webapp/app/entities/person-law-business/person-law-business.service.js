(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PersonLawBusiness', PersonLawBusiness);

    PersonLawBusiness.$inject = ['$resource'];

    function PersonLawBusiness ($resource) {
        var resourceUrl =  'api/person-law-businesses/:id';

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
